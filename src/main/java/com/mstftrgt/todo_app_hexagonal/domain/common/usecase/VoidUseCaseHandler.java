package com.mstftrgt.todo_app_hexagonal.domain.common.usecase;

import com.mstftrgt.todo_app_hexagonal.domain.common.model.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}