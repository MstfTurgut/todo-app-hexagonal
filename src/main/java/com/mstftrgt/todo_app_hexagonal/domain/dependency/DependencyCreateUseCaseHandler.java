package com.mstftrgt.todo_app_hexagonal.domain.dependency;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.exception.DependencyLoopException;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.port.DependencyPort;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.usecase.DependencyCreate;
import com.mstftrgt.todo_app_hexagonal.domain.item.exception.CannotCreateDependencyBetweenUncommonItemsException;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.service.ItemOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DependencyCreateUseCaseHandler implements UseCaseHandler<Dependency, DependencyCreate> {

    private final ItemPort itemPort;
    private final DependencyPort dependencyPort;
    private final ItemOwnershipValidator itemOwnershipValidator;

    @Override
    public Dependency handle(DependencyCreate useCase) {
        Item item = itemPort.retrieveAndValidate(useCase.getItemId());
        Item dependentItem = itemPort.retrieveAndValidate(useCase.getDependentItemId());

        validateBothItemsBelongToCurrentUser(useCase, item, dependentItem);

        validateItemsAreInTheSameList(item, dependentItem);

        validateNewDependencyNotCausingALoop(item.getId(), dependentItem.getId());

        return dependencyPort.create(useCase);
    }

    private void validateBothItemsBelongToCurrentUser(DependencyCreate useCase, Item item, Item dependentItem) {
        itemOwnershipValidator.validateItemBelongsToCurrentUserOrElseThrow(item, useCase.getCurrentUserId());
        itemOwnershipValidator.validateItemBelongsToCurrentUserOrElseThrow(dependentItem, useCase.getCurrentUserId());
    }

    private void validateItemsAreInTheSameList(Item item, Item dependentItem) {
        if (!item.isInTheSameListWith(dependentItem)) {
            throw new CannotCreateDependencyBetweenUncommonItemsException();
        }
    }

    private void validateNewDependencyNotCausingALoop(String itemId, String dependentItemId) {
        Optional<Dependency> inverseDependencyOptional = dependencyPort.retrieve(dependentItemId, itemId);

        if(inverseDependencyOptional.isPresent()) {
            throw new DependencyLoopException();
        }
    }
}
