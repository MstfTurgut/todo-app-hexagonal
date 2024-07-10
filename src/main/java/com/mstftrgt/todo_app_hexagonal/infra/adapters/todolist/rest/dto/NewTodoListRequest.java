package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest.dto;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTodoListRequest {

    @NotBlank
    private String name;

    public TodoListCreate toUseCase(String currentUserId) {
        return TodoListCreate.builder()
                .name(name)
                .currentUserId(currentUserId)
                .build();
    }
}
