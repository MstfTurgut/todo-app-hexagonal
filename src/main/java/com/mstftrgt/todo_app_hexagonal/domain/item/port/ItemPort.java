package com.mstftrgt.todo_app_hexagonal.domain.item.port;

import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemCreate;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemStatusChange;

import java.util.List;

public interface ItemPort {

    void delete(String itemId);

    Item create(ItemCreate itemCreate);

    Item retrieveAndValidate(String itemId);

    void deleteAllForList(String todoListId);

    List<Item> retrieveAll(String todoListId);

    void updateStatus(ItemStatusChange itemStatusChange);
}
