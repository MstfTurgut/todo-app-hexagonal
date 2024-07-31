package com.mstftrgt.todo_app_hexagonal.domain.adapters;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.port.TodoListPort;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class TodoListFakeDataAdapter implements TodoListPort {
    @Override
    public void delete(String todoListId) {}

    @Override
    public TodoList create(TodoListCreate todoListCreate) {
        return TodoList.builder()
                .id("testId")
                .name(todoListCreate.getName())
                .createDate(LocalDateTime.of(2002, Month.DECEMBER, 15, 0, 0))
                .userId(todoListCreate.getCurrentUserId())
                .build();
    }

    @Override
    public TodoList retrieve(String todoListId, String userId) {
        return TodoList.builder()
                .id(todoListId)
                .name("testName")
                .createDate(LocalDateTime.of(2002, Month.DECEMBER, 15, 0, 0))
                .userId(userId)
                .build();
    }

    @Override
    public boolean isTodoListNameByUserUnique(TodoListCreate todoListCreate) {
        return todoListCreate.getName().equals("uniqueTodoListName");
    }

    @Override
    public List<TodoList> retrieveAll(TodoListRetrieveAll todoListRetrieveAll) {
        TodoList todoList1 = TodoList.builder()
                .id("testId1")
                .name("testName1")
                .createDate(LocalDateTime.of(2002, Month.DECEMBER, 15, 0, 0))
                .userId(todoListRetrieveAll.getCurrentUserId())
                .build();

        TodoList todoList2 = TodoList.builder()
                .id("testId2")
                .name("testName2")
                .createDate(LocalDateTime.of(2002, Month.DECEMBER, 15, 0, 0))
                .userId(todoListRetrieveAll.getCurrentUserId())
                .build();

        return List.of(todoList1, todoList2);
    }
}
