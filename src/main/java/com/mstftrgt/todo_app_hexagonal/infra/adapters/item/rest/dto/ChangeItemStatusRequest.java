package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeItemStatusRequest {

    private Boolean status;

    public boolean isMarked() {
        return status;
    }
}
