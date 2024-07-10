package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa;

import com.mstftrgt.todo_app_hexagonal.domain.common.exception.TodoListNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.entity.TodoListEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoListDataAdapter implements TodoListPort {

    private final TodoListRepository todoListRepository;


    @Override
    public TodoList create(TodoListCreate todoListCreate) {
        TodoListEntity todoListEntity = TodoListEntity.builder()
                .name(todoListCreate.getName())
                .userId(todoListCreate.getCurrentUserId())
                .build();

        return todoListRepository.save(todoListEntity).toModel();
    }

    @Override
    public void delete(String todoListId) {
        TodoListEntity todoListEntity = todoListRepository
                .findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(todoListId));

        todoListRepository.delete(todoListEntity);
    }

    @Override
    public List<TodoList> retrieveAll(TodoListRetrieveAll todoListRetrieveAll) {
        List<TodoListEntity> todoListEntities = todoListRepository.findByUserId(todoListRetrieveAll.getCurrentUserId());

        return todoListEntities.stream().map(TodoListEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public TodoList retrieveAndValidate(String todoListId) {
        return todoListRepository
                .findById(todoListId)
                .orElseThrow(() -> new TodoListNotFoundException(todoListId))
                .toModel();
    }

    @Override
    public boolean isTodoListNameByUserUnique(TodoListCreate todoListCreate) {
        return todoListRepository
                .findByNameAndUserId(todoListCreate.getName(), todoListCreate.getCurrentUserId())
                .isEmpty();
    }
}
