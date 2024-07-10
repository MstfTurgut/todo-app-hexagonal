package com.mstftrgt.todo_app_hexagonal.domain.todolist.port;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;

import java.util.List;

public interface TodoListPort {

    void delete(String todoListId);

    TodoList create(TodoListCreate todoListCreate);

    TodoList retrieveAndValidate(String todoListId);

    boolean isTodoListNameByUserUnique(TodoListCreate todoListCreate);

    List<TodoList> retrieveAll(TodoListRetrieveAll todoListRetrieveAll);
}
