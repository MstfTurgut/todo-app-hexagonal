package com.mstftrgt.todo_app_hexagonal.domain.todolist.exception;

public class TodoListAlreadyExistsException extends RuntimeException{
    public TodoListAlreadyExistsException(String listName) {
        super("List already exists by name : " + listName);
    }
}
