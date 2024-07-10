package com.mstftrgt.todo_app_hexagonal.domain.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("User not found for username : " + username);
    }
}
