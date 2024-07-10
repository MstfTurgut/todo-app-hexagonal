package com.mstftrgt.todo_app_hexagonal.domain.dependency.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DependencyCreate implements UseCase {

    private String itemId;
    private String currentUserId;
    private String dependentItemId;
}
