package com.mstftrgt.todo_app_hexagonal.domain.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class TodoList {

    private String id;
    private String name;
    private String userId;
    private LocalDateTime createDate;

    public boolean belongToUser(String userId) {
        return this.getUserId().equals(userId);
    }
}
