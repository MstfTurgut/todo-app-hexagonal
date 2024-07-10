package com.mstftrgt.todo_app_hexagonal.domain.dependency.exception;

public class DependencyLoopException extends RuntimeException {
    public DependencyLoopException() {
        super("Cannot create dependency, this dependency causing a dependency loop.");
    }
}
