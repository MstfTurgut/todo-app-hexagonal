package com.mstftrgt.todo_app_hexagonal.domain.dependency.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dependency {

    private String id;
    private String itemId;
    private String dependentItemId;
}
