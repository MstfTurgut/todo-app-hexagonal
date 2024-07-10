package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.service.TodoListOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCreateUseCaseHandler implements UseCaseHandler<Item, ItemCreate> {

    private final ItemPort itemPort;
    private final TodoListPort todoListPort;
    private final TodoListOwnershipValidator todoListOwnershipValidator;

    @Override
    public Item handle(ItemCreate useCase) {
        validateTodoListExistsAndBelongsToCurrentUser(useCase);

        return itemPort.create(useCase);
    }

    private void validateTodoListExistsAndBelongsToCurrentUser(ItemCreate useCase) {
        TodoList todoList = todoListPort.retrieveAndValidate(useCase.getTodoListId());

        todoListOwnershipValidator
                .validateTodoListBelongsToCurrentUserOrElseThrow(todoList, useCase.getCurrentUserId());
    }

}
