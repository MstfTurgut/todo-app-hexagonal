package com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TodoListCreate implements UseCase {

    private String name;
    private String currentUserId;
}
