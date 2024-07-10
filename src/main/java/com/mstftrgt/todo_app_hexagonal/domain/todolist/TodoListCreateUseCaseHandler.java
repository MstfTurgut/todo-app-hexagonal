package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.exception.TodoListAlreadyExistsException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TodoListCreateUseCaseHandler implements UseCaseHandler<TodoList, TodoListCreate> {

    private final TodoListPort todoListPort;

    @Override
    public TodoList handle(TodoListCreate useCase) {
        validateTodoListNameUniquenessByUserOrElseThrow(useCase);

        return todoListPort.create(useCase);
    }

    private void validateTodoListNameUniquenessByUserOrElseThrow(TodoListCreate useCase) {
        boolean unique = todoListPort.isTodoListNameByUserUnique(useCase);

        if(!unique) {
            throw new TodoListAlreadyExistsException(useCase.getName());
        }
    }

}
