package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCreateUseCaseHandler implements UseCaseHandler<Item, ItemCreate> {

    private final ItemPort itemPort;
    private final TodoListPort todoListPort;

    @Override
    public Item handle(ItemCreate useCase) {
        todoListPort.retrieve(useCase.getTodoListId(), useCase.getCurrentUserId());
        return itemPort.create(useCase);
    }
}
