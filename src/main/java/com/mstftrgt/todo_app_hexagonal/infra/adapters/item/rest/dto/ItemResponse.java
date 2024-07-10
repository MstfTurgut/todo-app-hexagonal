package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.rest.dto;

import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {

    private String id;
    private String name;
    private Boolean status;
    private LocalDateTime deadline;
    private LocalDateTime createDate;
    private String todoListId;

    public static ItemResponse from(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .status(item.getStatus())
                .deadline(item.getDeadline())
                .createDate(item.getCreateDate())
                .todoListId(item.getTodoListId())
                .build();
    }
}
