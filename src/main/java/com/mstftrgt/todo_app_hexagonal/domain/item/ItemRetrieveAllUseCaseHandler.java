package com.mstftrgt.todo_app_hexagonal.domain.item;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.item.usecase.ItemRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRetrieveAllUseCaseHandler implements UseCaseHandler<List<Item>, ItemRetrieveAll> {

    private final ItemPort itemPort;
    private final TodoListPort todoListPort;

    @Override
    public List<Item> handle(ItemRetrieveAll useCase) {
        todoListPort.retrieve(useCase.getTodoListId(), useCase.getCurrentUserId());
        return itemPort.retrieveAll(useCase.getTodoListId());
    }
}
