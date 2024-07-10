package com.mstftrgt.todo_app_hexagonal.domain.item.service;

import com.mstftrgt.todo_app_hexagonal.domain.item.exception.ItemNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemOwnershipValidator {

    private final TodoListPort todoListPort;

    public void validateItemBelongsToCurrentUserOrElseThrow(Item item, String currentUserId) {
        TodoList todoList = todoListPort.retrieveAndValidate(item.getTodoListId());

        if(!todoList.belongToUser(currentUserId)) {
            throw new ItemNotFoundException(item.getId());
        }
    }

}
