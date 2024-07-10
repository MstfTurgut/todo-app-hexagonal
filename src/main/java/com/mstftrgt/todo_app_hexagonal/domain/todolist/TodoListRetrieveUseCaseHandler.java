package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.service.TodoListOwnershipValidator;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TodoListRetrieveUseCaseHandler implements UseCaseHandler<TodoList, TodoListRetrieve> {

    private final TodoListPort todoListPort;
    private final TodoListOwnershipValidator todoListOwnershipValidator;

    @Override
    public TodoList handle(TodoListRetrieve useCase) {
        TodoList todoList = todoListPort.retrieveAndValidate(useCase.getTodoListId());

        todoListOwnershipValidator.validateTodoListBelongsToCurrentUserOrElseThrow(todoList, useCase.getCurrentUserId());

        return todoList;
    }
}
