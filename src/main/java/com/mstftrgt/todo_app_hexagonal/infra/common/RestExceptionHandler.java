package com.mstftrgt.todo_app_hexagonal.infra.common;

import com.mstftrgt.todo_app_hexagonal.domain.common.exception.TodoListNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.exception.DependencyLoopException;
import com.mstftrgt.todo_app_hexagonal.domain.item.exception.CannotCreateDependencyBetweenUncommonItemsException;
import com.mstftrgt.todo_app_hexagonal.domain.item.exception.CannotMarkItemCompletedBeforeTheDependentItemException;
import com.mstftrgt.todo_app_hexagonal.domain.item.exception.ItemNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.exception.TodoListAlreadyExistsException;
import com.mstftrgt.todo_app_hexagonal.domain.user.exception.UserNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.user.exception.UsernameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TodoListNotFoundException.class)
    protected ErrorResponse handleListNotFoundException(TodoListNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TodoListAlreadyExistsException.class)
    protected ErrorResponse handleListAlreadyExistsException(TodoListAlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    protected ErrorResponse handleItemNotFoundException(ItemNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotMarkItemCompletedBeforeTheDependentItemException.class)
    protected ErrorResponse handleCannotMarkItemCompletedBeforeTheDependentItemException(CannotMarkItemCompletedBeforeTheDependentItemException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotCreateDependencyBetweenUncommonItemsException.class)
    protected ErrorResponse handleCannotCreateDependencyBetweenUncommonItemsException(CannotCreateDependencyBetweenUncommonItemsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DependencyLoopException.class)
    protected ErrorResponse handleDependencyLoopException(DependencyLoopException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyInUseException.class)
    protected ErrorResponse handleUsernameAlreadyInUseException(UsernameAlreadyInUseException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
