package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.adapters.TodoListFakeDataAdapter;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoListRetrieveTest {

    TodoListRetrieveUseCaseHandler todoListRetrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        todoListRetrieveUseCaseHandler = new TodoListRetrieveUseCaseHandler(new TodoListFakeDataAdapter());
    }

    @Test
    void should_retrieve_todoList() {

        TodoListRetrieve todoListRetrieve = TodoListRetrieve.builder()
                .todoListId("testId")
                .currentUserId("testUserId")
                .build();

        TodoList todoList = todoListRetrieveUseCaseHandler.handle(todoListRetrieve);

        assertThat(todoList).isNotNull()
                .returns(todoListRetrieve.getTodoListId(), TodoList::getId)
                .returns("testName", TodoList::getName)
                .returns(todoListRetrieve.getCurrentUserId(), TodoList::getUserId);
    }

}
