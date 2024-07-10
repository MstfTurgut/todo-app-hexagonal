package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.rest.dto;

import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DependencyResponse {

    private String id;
    private String itemId;
    private String dependentItemId;

    public static DependencyResponse from(Dependency dependency) {
        return DependencyResponse.builder()
                .id(dependency.getId())
                .itemId(dependency.getItemId())
                .dependentItemId(dependency.getDependentItemId())
                .build();
    }
}
