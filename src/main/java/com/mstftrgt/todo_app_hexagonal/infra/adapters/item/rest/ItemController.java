package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemCreate;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemDelete;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemRetrieve;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemStatusChange;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto.ChangeItemStatusRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto.ItemResponse;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto.NewItemRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {

    private final UseCaseHandler<Item, ItemCreate> itemCreateUseCaseHandler;
    private final VoidUseCaseHandler<ItemDelete> itemDeleteVoidUseCaseHandler;
    private final UseCaseHandler<Item, ItemRetrieve> itemRetrieveUseCaseHandler;
    private final VoidUseCaseHandler<ItemStatusChange> itemStatusChangeVoidUseCaseHandler;
    private final UseCaseHandler<List<Item>, ItemRetrieveAll> itemRetrieveAllUseCaseHandler;


    @GetMapping("{todoListId}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ItemResponse> getAllItemsByTodoListId(@PathVariable String todoListId) {
        ItemRetrieveAll useCase = new ItemRetrieveAll(todoListId, getCurrentUserId());
        return itemRetrieveAllUseCaseHandler.handle(useCase).stream().map(ItemResponse::from).toList();
    }

    @GetMapping("/get/{itemId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse getItemById(@PathVariable String itemId) {
        ItemRetrieve useCase = new ItemRetrieve(itemId, getCurrentUserId());
        return ItemResponse.from(itemRetrieveUseCaseHandler.handle(useCase));
    }

    @PostMapping("{todoListId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemResponse createItem(@Valid @RequestBody NewItemRequest itemRequest, @PathVariable String todoListId, UriComponentsBuilder ucb, HttpServletResponse response) {
        ItemCreate useCase = new ItemCreate(itemRequest.getName(), todoListId, getCurrentUserId(), itemRequest.getDeadline());
        Item item = itemCreateUseCaseHandler.handle(useCase);
        setLocationHeader(ucb, response, item.getId());
        return ItemResponse.from(item);
    }

    private void setLocationHeader(UriComponentsBuilder ucb, HttpServletResponse response, String itemId) {
        URI locationOfNewItem = ucb.path("items/get/{itemId}").buildAndExpand(itemId).toUri();
        response.setHeader("Location", locationOfNewItem.toString());
    }

    @DeleteMapping("{itemId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String itemId) {
        ItemDelete useCase = new ItemDelete(itemId, getCurrentUserId());
        itemDeleteVoidUseCaseHandler.handle(useCase);
    }

    @PatchMapping("{itemId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateItemStatus(@PathVariable String itemId, @Valid @RequestBody ChangeItemStatusRequest changeStatusRequest) {
        ItemStatusChange useCase = new ItemStatusChange(itemId, changeStatusRequest.getStatus(), getCurrentUserId());
        itemStatusChangeVoidUseCaseHandler.handle(useCase);
    }
    private String getCurrentUserId() {
        UserEntity userEntity = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntity.getId();
    }

}
