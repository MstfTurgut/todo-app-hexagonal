package com.mstftrgt.todo_app_hexagonal.domain.item.exception;

public class CannotMarkItemCompletedBeforeTheDependentItemException extends  RuntimeException {
    public CannotMarkItemCompletedBeforeTheDependentItemException() {
        super("This item has dependency to other items, mark them first.");
    }
}
