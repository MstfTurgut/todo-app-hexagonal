package com.mstftrgt.todo_app_hexagonal.domain.dependency.port;

import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.usecase.DependencyCreate;

import java.util.List;
import java.util.Optional;

public interface DependencyPort {

    void deleteAllForItem(String itemId);

    List<Dependency> retrieveAllForItem(String itemId);

    Dependency create(DependencyCreate dependencyCreate);

    Optional<Dependency> retrieve(String itemId, String dependentItemId);
}
