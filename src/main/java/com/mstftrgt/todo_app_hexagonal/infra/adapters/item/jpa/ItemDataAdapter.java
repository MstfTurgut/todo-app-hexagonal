package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa;

import com.mstftrgt.todo_app_hexagonal.domain.item.exception.ItemNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemCreate;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemStatusChange;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa.entity.ItemEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemDataAdapter implements ItemPort {

    private final ItemRepository itemRepository;


    @Override
    public List<Item> retrieveAll(String todoListId) {
        List<ItemEntity> itemEntities = itemRepository.findByTodoListId(todoListId);

        return itemEntities.stream()
                .map(ItemEntity::toModel)
                .toList();
    }

    @Override
    public Item retrieveAndValidate(String itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId))
                .toModel();
    }

    @Override
    public Item create(ItemCreate itemCreate) {
        ItemEntity itemEntity = ItemEntity.builder()
                .name(itemCreate.getName())
                .status(false)
                .deadline(itemCreate.getDeadline())
                .todoListId(itemCreate.getTodoListId())
                .build();

        return itemRepository.save(itemEntity).toModel();
    }

    @Override
    public void delete(String itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));

        itemRepository.delete(itemEntity);
    }

    @Override
    public void deleteAllForList(String todoListId) {
        itemRepository.deleteAllByTodoListId(todoListId);
    }

    @Override
    public void updateStatus(ItemStatusChange itemStatusChange) {
        itemRepository.updateItemStatusByItemId(itemStatusChange.getStatus(), itemStatusChange.getItemId());
    }
}
