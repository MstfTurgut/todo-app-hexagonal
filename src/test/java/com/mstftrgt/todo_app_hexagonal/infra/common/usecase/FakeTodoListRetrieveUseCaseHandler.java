package com.mstftrgt.todo_app_hexagonal.infra.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieve;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Primary
public class FakeTodoListRetrieveUseCaseHandler implements UseCaseHandler<TodoList, TodoListRetrieve> {
    @Override
    public TodoList handle(TodoListRetrieve useCase) {
        return TodoList.builder()
                .id(useCase.getTodoListId())
                .name("todoListName")
                .createDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .userId(useCase.getCurrentUserId())
                .build();
    }
}
