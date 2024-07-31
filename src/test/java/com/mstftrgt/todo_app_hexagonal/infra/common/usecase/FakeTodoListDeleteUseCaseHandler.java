package com.mstftrgt.todo_app_hexagonal.infra.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListDelete;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FakeTodoListDeleteUseCaseHandler implements VoidUseCaseHandler<TodoListDelete> {
    @Override
    public void handle(TodoListDelete useCase) {
    }
}
