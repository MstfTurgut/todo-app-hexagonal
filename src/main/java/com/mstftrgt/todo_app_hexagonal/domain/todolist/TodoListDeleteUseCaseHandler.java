package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.item.port.ItemPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.service.TodoListOwnershipValidator;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoListDeleteUseCaseHandler implements VoidUseCaseHandler<TodoListDelete> {

    private final ItemPort itemPort;
    private final TodoListPort todoListPort;
    private final TodoListOwnershipValidator todoListOwnershipValidator;

    @Override
    @Transactional
    public void handle(TodoListDelete useCase) {
        validateTodoListExistsAndBelongToCurrentUser(useCase);

        itemPort.deleteAllForList(useCase.getTodoListId());

        todoListPort.delete(useCase.getTodoListId());
    }

    private void validateTodoListExistsAndBelongToCurrentUser(TodoListDelete useCase) {
        TodoList todoList = todoListPort.retrieveAndValidate(useCase.getTodoListId());

        todoListOwnershipValidator.validateTodoListBelongsToCurrentUserOrElseThrow(todoList, useCase.getCurrentUserId());
    }
}
