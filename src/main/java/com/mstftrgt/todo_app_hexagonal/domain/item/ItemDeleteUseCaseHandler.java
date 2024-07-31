package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.dependency.port.DependencyPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemDelete;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemDeleteUseCaseHandler implements VoidUseCaseHandler<ItemDelete> {

    private final ItemPort itemPort;
    private final DependencyPort dependencyPort;
    private final TodoListPort todoListPort;

    @Override
    @Transactional
    public void handle(ItemDelete useCase) {
        validateItemExistsAndBelongsToCurrentUser(useCase);

        dependencyPort.deleteAllForItem(useCase.getItemId());

        itemPort.delete(useCase.getItemId());
    }

    private void validateItemExistsAndBelongsToCurrentUser(ItemDelete useCase) {
        Item item = itemPort.retrieveAndValidate(useCase.getItemId());
        todoListPort.retrieve(item.getTodoListId(), useCase.getCurrentUserId());
    }
}
