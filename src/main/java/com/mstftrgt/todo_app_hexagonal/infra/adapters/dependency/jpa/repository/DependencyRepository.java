package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa.repository;

import com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DependencyRepository extends JpaRepository<DependencyEntity, String> {

    List<DependencyEntity> findAllByItemId(String itemId);

    void deleteAllByItemIdOrDependentItemId(String itemId, String dependentItemId);

    Optional<DependencyEntity> findByItemIdAndDependentItemId(String itemId, String dependentItemId);
}
