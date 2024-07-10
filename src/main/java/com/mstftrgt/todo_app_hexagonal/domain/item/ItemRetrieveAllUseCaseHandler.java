package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.service.TodoListOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRetrieveAllUseCaseHandler implements UseCaseHandler<List<Item>, ItemRetrieveAll> {

    private final ItemPort itemPort;
    private final TodoListPort todoListPort;
    private final TodoListOwnershipValidator todoListOwnershipValidator;

    @Override
    public List<Item> handle(ItemRetrieveAll useCase) {
        validateTodoListExistsAndBelongsToCurrentUser(useCase);

        return itemPort.retrieveAll(useCase.getTodoListId());
    }

    private void validateTodoListExistsAndBelongsToCurrentUser(ItemRetrieveAll useCase) {
        TodoList todoList = todoListPort.retrieveAndValidate(useCase.getTodoListId());

        todoListOwnershipValidator
                .validateTodoListBelongsToCurrentUserOrElseThrow(todoList, useCase.getCurrentUserId());
    }
}
