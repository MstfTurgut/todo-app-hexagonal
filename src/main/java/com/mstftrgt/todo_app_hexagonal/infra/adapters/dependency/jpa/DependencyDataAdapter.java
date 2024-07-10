package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa;

import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.port.DependencyPort;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.usecase.DependencyCreate;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa.entity.DependencyEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa.repository.DependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DependencyDataAdapter implements DependencyPort {

    private final DependencyRepository dependencyRepository;

    @Override
    public List<Dependency> retrieveAllForItem(String itemId) {
        return dependencyRepository.findAllByItemId(itemId).stream().map(DependencyEntity::toModel).toList();
    }

    @Override
    public void deleteAllForItem(String itemId) {
        dependencyRepository.deleteAllByItemIdOrDependentItemId(itemId, itemId);
    }

    @Override
    public Dependency create(DependencyCreate dependencyCreate) {
        DependencyEntity dependencyEntity = DependencyEntity.builder()
                .itemId(dependencyCreate.getItemId())
                .dependentItemId(dependencyCreate.getDependentItemId())
                .build();

        return dependencyRepository.save(dependencyEntity).toModel();
    }

    @Override
    public Optional<Dependency> retrieve(String itemId, String dependentItemId) {
        Optional<DependencyEntity> dependencyEntityOptional = dependencyRepository
                .findByItemIdAndDependentItemId(itemId, dependentItemId);

        return dependencyEntityOptional.map(DependencyEntity::toModel);
    }
}
