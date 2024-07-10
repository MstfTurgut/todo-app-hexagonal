package com.mstftrgt.todo_app_hexagonal.domain.todolist.service;

import com.mstftrgt.todo_app_hexagonal.domain.common.exception.TodoListNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import org.springframework.stereotype.Service;

@Service
public class TodoListOwnershipValidator {

    public void validateTodoListBelongsToCurrentUserOrElseThrow(TodoList todoList, String currentUserId) {
        if(!todoList.belongToUser(currentUserId)) {
            throw new TodoListNotFoundException(todoList.getId());
        }
    }
}
