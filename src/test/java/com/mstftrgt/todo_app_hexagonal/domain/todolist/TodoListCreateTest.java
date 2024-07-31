package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.adapters.TodoListFakeDataAdapter;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.exception.TodoListAlreadyExistsException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TodoListCreateTest {

    TodoListCreateUseCaseHandler todoListCreateUseCaseHandler;

    @BeforeEach
    void setUp() {
        todoListCreateUseCaseHandler = new TodoListCreateUseCaseHandler(new TodoListFakeDataAdapter());
    }

    @Test
    void should_create_todoList_when_given_todoListName_is_unique_for_user() {

        TodoListCreate todoListCreate = TodoListCreate.builder()
                .name("uniqueTodoListName")
                .currentUserId("testUserId")
                .build();

        TodoList todoList = todoListCreateUseCaseHandler.handle(todoListCreate);

        assertThat(todoList).isNotNull()
                .returns("testId", TodoList::getId)
                .returns(todoListCreate.getName(), TodoList::getName)
                .returns(todoListCreate.getCurrentUserId(), TodoList::getUserId)
                .returns(LocalDateTime.of(2002, Month.DECEMBER, 15, 0, 0), TodoList::getCreateDate);
    }

    @Test
    void should_not_create_todoList_when_given_todoListName_is_not_unique_for_user() {

        TodoListCreate todoListCreate = TodoListCreate.builder()
                .name("notUniqueTodoListName")
                .currentUserId("testUserId")
                .build();

        assertThatExceptionOfType(TodoListAlreadyExistsException.class)
                .isThrownBy(() -> todoListCreateUseCaseHandler.handle(todoListCreate))
                .withMessage("List already exists by name : " + todoListCreate.getName());

    }

}
