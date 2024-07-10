package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDependencyRequest {

    private String dependentItemId;
}
