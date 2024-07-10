package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.port.DependencyPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.exception.CannotMarkItemCompletedBeforeTheDependentItemException;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.service.ItemOwnershipValidator;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemStatusChange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemStatusChangeUseCaseHandler implements VoidUseCaseHandler<ItemStatusChange> {

    private final ItemPort itemPort;
    private final DependencyPort dependencyPort;
    private final ItemOwnershipValidator itemOwnershipValidator;

    @Override
    public void handle(ItemStatusChange useCase) {
        validateItemExistsAndBelongsToCurrentUser(useCase);

        validateAllDependenciesAreMarked(useCase.getItemId());

        itemPort.updateStatus(useCase);
    }

    private void validateItemExistsAndBelongsToCurrentUser(ItemStatusChange useCase) {
        Item item = itemPort.retrieveAndValidate(useCase.getItemId());

        itemOwnershipValidator.validateItemBelongsToCurrentUserOrElseThrow(item, useCase.getCurrentUserId());
    }

    private void validateAllDependenciesAreMarked(String itemId) {
        List<Dependency> dependencies = dependencyPort.retrieveAllForItem(itemId);

        dependencies.forEach((d) -> {
            Item dependentItem = itemPort.retrieveAndValidate(d.getDependentItemId());
            validateDependentItemIsMarkedOrElseThrow(dependentItem);
        });
    }

    private void validateDependentItemIsMarkedOrElseThrow(Item dependentItem) {
        if(!dependentItem.isMarked()) {
            throw new CannotMarkItemCompletedBeforeTheDependentItemException();
        }
    }
}
