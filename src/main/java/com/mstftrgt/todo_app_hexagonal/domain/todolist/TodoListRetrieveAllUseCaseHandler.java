package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListRetrieveAllUseCaseHandler implements UseCaseHandler<List<TodoList>, TodoListRetrieveAll> {

    private final TodoListPort todoListPort;

    @Override
    public List<TodoList> handle(TodoListRetrieveAll useCase) {
        return todoListPort.retrieveAll(useCase);
    }
}
