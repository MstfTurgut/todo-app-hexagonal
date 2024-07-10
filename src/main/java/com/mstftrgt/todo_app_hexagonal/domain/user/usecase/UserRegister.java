package com.mstftrgt.todo_app_hexagonal.domain.user.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegister implements UseCase {

    private String username;
    private String password;
}
