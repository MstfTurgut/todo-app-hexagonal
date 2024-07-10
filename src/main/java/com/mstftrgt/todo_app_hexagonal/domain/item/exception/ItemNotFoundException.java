package com.mstftrgt.todo_app_hexagonal.domain.item.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String itemId) {
        super("Item not found by id : " + itemId);
    }
}