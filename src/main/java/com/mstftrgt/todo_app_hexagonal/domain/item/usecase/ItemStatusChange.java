package com.mstftrgt.todo_app_hexagonal.domain.item.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemStatusChange implements UseCase {

    private String itemId;
    private Boolean status;
    private String currentUserId;
}