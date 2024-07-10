package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.rest;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.usecase.DependencyCreate;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.rest.dto.DependencyResponse;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.rest.dto.NewDependencyRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("dependencies")
public class DependencyController {

    private final UseCaseHandler<Dependency, DependencyCreate> dependencyCreateUseCaseHandler;

    @PostMapping("{itemId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DependencyResponse addDependency(@PathVariable String itemId, @Valid @RequestBody NewDependencyRequest newDependencyRequest) {
        DependencyCreate useCase = new DependencyCreate(itemId, newDependencyRequest.getDependentItemId(), getCurrentUserId());
        return DependencyResponse.from(dependencyCreateUseCaseHandler.handle(useCase));
    }

    private String getCurrentUserId() {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntity.getId();
    }
}
