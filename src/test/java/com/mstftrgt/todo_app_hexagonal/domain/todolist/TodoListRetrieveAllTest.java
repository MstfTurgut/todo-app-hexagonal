package com.mstftrgt.todo_app_hexagonal.domain.todolist;

import com.mstftrgt.todo_app_hexagonal.domain.adapters.TodoListFakeDataAdapter;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoListRetrieveAllTest {

    TodoListRetrieveAllUseCaseHandler todoListRetrieveAllUseCaseHandler;

    @BeforeEach
    void setUp() {
        todoListRetrieveAllUseCaseHandler = new TodoListRetrieveAllUseCaseHandler(new TodoListFakeDataAdapter());
    }


    @Test
    void should_retrieve_all_todoLists() {

        TodoListRetrieveAll todoListRetrieveAll = TodoListRetrieveAll.builder()
                .currentUserId("testUserId")
                .build();

        List<TodoList> todoLists = todoListRetrieveAllUseCaseHandler.handle(todoListRetrieveAll);

        assertThat(todoLists).isNotNull().hasSize(2);

        TodoList todoList1 = todoLists.get(0);
        TodoList todoList2 = todoLists.get(1);


        assertThat(todoList1).isNotNull()
                .returns("testId1", TodoList::getId)
                .returns("testName1", TodoList::getName)
                .returns(todoListRetrieveAll.getCurrentUserId(), TodoList::getUserId);

        assertThat(todoList2).isNotNull()
                .returns("testId2", TodoList::getId)
                .returns("testName2", TodoList::getName)
                .returns(todoListRetrieveAll.getCurrentUserId(), TodoList::getUserId);
    }


}
