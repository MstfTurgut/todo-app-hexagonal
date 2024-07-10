package com.mstftrgt.todo_app_hexagonal.domain.item.exception;

public class CannotCreateDependencyBetweenUncommonItemsException extends RuntimeException {
    public CannotCreateDependencyBetweenUncommonItemsException() {
        super("Items are not in the same list, dependency creation failed.");
    }
}
