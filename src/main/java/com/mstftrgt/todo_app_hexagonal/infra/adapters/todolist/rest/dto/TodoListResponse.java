package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest.dto;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoListResponse {

    private String id;
    private String name;
    private LocalDateTime createDate;

    public static TodoListResponse from(TodoList todoList) {
        return TodoListResponse.builder()
                .id(todoList.getId())
                .name(todoList.getName())
                .createDate(todoList.getCreateDate())
                .build();
    }
}
