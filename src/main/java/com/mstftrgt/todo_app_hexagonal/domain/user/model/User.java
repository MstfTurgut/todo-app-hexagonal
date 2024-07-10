package com.mstftrgt.todo_app_hexagonal.domain.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;
    private String username;
    private String password;
}
