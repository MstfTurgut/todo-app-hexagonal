package com.mstftrgt.todo_app_hexagonal.domain.item.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
public class ItemCreate implements UseCase {

    private String name;
    private String todoListId;
    private String currentUserId;
    private LocalDateTime deadline;
}

