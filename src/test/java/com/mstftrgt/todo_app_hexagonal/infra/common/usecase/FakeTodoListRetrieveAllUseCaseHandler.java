package com.mstftrgt.todo_app_hexagonal.infra.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
public class FakeTodoListRetrieveAllUseCaseHandler implements UseCaseHandler<List<TodoList>, TodoListRetrieveAll> {
    @Override
    public List<TodoList> handle(TodoListRetrieveAll useCase) {
        TodoList todoList1 = TodoList.builder()
                .id("todoListId1")
                .name("todoListName1")
                .userId(useCase.getCurrentUserId())
                .createDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .build();

        TodoList todoList2 = TodoList.builder()
                .id("todoListId2")
                .name("todoListName2")
                .userId(useCase.getCurrentUserId())
                .createDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .build();

        return List.of(todoList1, todoList2);
    }
}
