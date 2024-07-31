package com.mstftrgt.todo_app_hexagonal.infra.integration;

import com.mstftrgt.todo_app_hexagonal.domain.common.exception.TodoListNotFoundException;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.TodoListDataAdapter;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.entity.TodoListEntity;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.repository.TodoListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/todolists.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TodoListDataAdapterIT {

    @Autowired
    TodoListDataAdapter todoListDataAdapter;

    @Autowired
    TodoListRepository todoListRepository;

    @Test
    void should_create_todolist() {
        // given
        TodoListCreate todoListCreate = TodoListCreate.builder()
                .name("newTodoList")
                .currentUserId("userId1")
                .build();

        // when
        TodoList createdTodoList = todoListDataAdapter.create(todoListCreate);

        // then
        Optional<TodoListEntity> todoListEntity = todoListRepository.findById(createdTodoList.getId());
        assertThat(todoListEntity).isPresent();
        assertThat(todoListEntity.get().toModel()).isEqualTo(createdTodoList);
    }


    @Test
    void should_delete_todolist_when_todolist_exists() {
        String todoListIdToDelete = "todoListId1";

        Optional<TodoListEntity> existingTodoList = todoListRepository.findById(todoListIdToDelete);
        assertThat(existingTodoList).isPresent();

        todoListDataAdapter.delete("todoListId1");

        Optional<TodoListEntity> deletedTodoList = todoListRepository.findById(todoListIdToDelete);
        assertThat(deletedTodoList).isEmpty();
    }

    @Test
    void should_not_delete_todolist_when_todolist_not_exists() {
        String todoListIdToDelete = "nonExistingTodoListId";

        Optional<TodoListEntity> existingTodoList = todoListRepository.findById(todoListIdToDelete);
        assertThat(existingTodoList).isEmpty();

        assertThatExceptionOfType(TodoListNotFoundException.class)
                .isThrownBy(() -> todoListDataAdapter.delete(todoListIdToDelete))
                .withMessage("List not found for id : " + todoListIdToDelete);
    }

    @Test
    void should_retrieve_all_todoLists() {
        TodoListRetrieveAll todoListRetrieveAll = new TodoListRetrieveAll("userId1");

        List<TodoList> todoLists = todoListDataAdapter.retrieveAll(todoListRetrieveAll);

        assertThat(todoLists).isNotNull().hasSize(2)
                .extracting("id", "name", "userId", "createDate")
                .containsExactlyInAnyOrder(
                        tuple("todoListId1", "todoListName1", "userId1", LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                        tuple("todoListId2", "todoListName2", "userId1", LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                        );
    }

    @Test
    void should_retrieve_todolist_when_todolist_exists() {
        String todoListIdToRetrieve = "todoListId1";
        String userId = "userId1";

        Optional<TodoListEntity> existingTodoList = todoListRepository.findByIdAndUserId(todoListIdToRetrieve, userId);
        assertThat(existingTodoList).isPresent();

        TodoList todoList = todoListDataAdapter.retrieve(todoListIdToRetrieve, userId);

        assertThat(todoList).isNotNull()
                .returns(todoListIdToRetrieve, TodoList::getId)
                .returns("todoListName1", TodoList::getName)
                .returns(LocalDateTime.of(2024, 1, 1, 0, 0, 0), TodoList::getCreateDate)
                .returns(userId, TodoList::getUserId);
    }

    @Test
    void should_not_retrieve_todolist_when_todolist_not_exists() {
        String todoListIdToRetrieve = "nonExistingTodoList";
        String userId = "userId1";

        Optional<TodoListEntity> existingTodoList = todoListRepository.findByIdAndUserId(todoListIdToRetrieve, userId);
        assertThat(existingTodoList).isEmpty();

        assertThatExceptionOfType(TodoListNotFoundException.class)
                .isThrownBy(() -> todoListDataAdapter.delete(todoListIdToRetrieve))
                .withMessage("List not found for id : " + todoListIdToRetrieve);
    }

    @Test
    void should_return_true_when_given_todoListName_is_unique() {
        TodoListCreate todoListCreateWithUniqueName = new TodoListCreate("uniqueTodoListName", "userId1");
        assertThat(todoListDataAdapter.isTodoListNameByUserUnique(todoListCreateWithUniqueName)).isTrue();
    }

    @Test
    void should_return_false_when_given_todoListName_is_not_unique() {
        TodoListCreate todoListCreateWithExistingName = new TodoListCreate("todoListName1", "userId1");
        assertThat(todoListDataAdapter.isTodoListNameByUserUnique(todoListCreateWithExistingName)).isFalse();
    }

}
