package com.mstftrgt.todo_app_hexagonal.domain.common.exception;

public class TodoListNotFoundException extends RuntimeException {
    public TodoListNotFoundException(String todoListId) {
        super("List not found for id : " + todoListId);
    }
}