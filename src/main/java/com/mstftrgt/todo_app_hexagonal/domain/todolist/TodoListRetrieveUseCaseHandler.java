package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TodoListRetrieveUseCaseHandler implements UseCaseHandler<TodoList, TodoListRetrieve> {

    private final TodoListPort todoListPort;

    @Override
    public TodoList handle(TodoListRetrieve useCase) {
        return todoListPort.retrieve(useCase.getTodoListId(), useCase.getCurrentUserId());
    }
}
