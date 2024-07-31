package com.mstftrgt.todo_app_hexagonal.infra.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Primary
public class FakeTodoListCreateUseCaseHandler implements UseCaseHandler<TodoList, TodoListCreate> {

    @Override
    public TodoList handle(TodoListCreate useCase) {
        return TodoList.builder()
                .id("todoListId")
                .name(useCase.getName())
                .createDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .userId(useCase.getCurrentUserId())
                .build();
    }
}
