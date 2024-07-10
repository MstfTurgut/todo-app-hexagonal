package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.service.ItemOwnershipValidator;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemRetrieveUseCaseHandler implements UseCaseHandler<Item, ItemRetrieve> {

    private final ItemPort itemPort;
    private final ItemOwnershipValidator itemOwnershipValidator;

    @Override
    public Item handle(ItemRetrieve useCase) {
        Item item = itemPort.retrieveAndValidate(useCase.getItemId());

        itemOwnershipValidator.validateItemBelongsToCurrentUserOrElseThrow(item, useCase.getCurrentUserId());

        return item;
    }
}
