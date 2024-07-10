package com.mstftrgt.todo_app_hexagonal.domain.item.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Item {

    private String id;
    private String name;
    private Boolean status;
    private String todoListId;
    private LocalDateTime deadline;
    private LocalDateTime createDate;

    public boolean isMarked() {
        return this.status;
    }

    public boolean isInTheSameListWith(Item item) {
        return this.getTodoListId().equals(item.getTodoListId());
    }
}
