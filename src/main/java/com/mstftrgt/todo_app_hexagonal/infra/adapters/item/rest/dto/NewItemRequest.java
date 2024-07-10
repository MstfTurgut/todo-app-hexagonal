package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewItemRequest {

    @NotBlank
    private String name;
    private LocalDateTime deadline;
}
