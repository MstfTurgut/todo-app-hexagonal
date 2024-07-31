package com.mstftrgt.todo_app_hexagonal.infra.integration;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest.dto.TodoListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:sql/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TodoListControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void should_create_todolist() {
        TodoListCreate todoListCreate = TodoListCreate.builder()
                .name("newTodoListName")
                .currentUserId("testId")
                .build();

        var responseEntity = testRestTemplate
                .withBasicAuth("testUsername", "testPassword")
                .exchange("/todo-lists", HttpMethod.POST, new HttpEntity<>(todoListCreate, null), TodoListResponse.class);

        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.CREATED, from(ResponseEntity::getStatusCode));

        assertThat(responseEntity.getBody()).isNotNull()
                .returns("todoListId", from(TodoListResponse::getId))
                .returns(todoListCreate.getName(), from(TodoListResponse::getName))
                .returns(LocalDateTime.of(2024, 1, 1 , 0, 0, 0), from(TodoListResponse::getCreateDate));
    }

    @Test
    void should_retrieve_all_todo_lists() {
        ResponseEntity<List<TodoList>> todoListsResponse = testRestTemplate
                .withBasicAuth("testUsername", "testPassword")
                .exchange("/todo-lists", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(todoListsResponse).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        assertThat(todoListsResponse.getBody())
                .isNotNull()
                .hasSize(2)
                .extracting("id", "name", "createDate")
                .containsExactlyInAnyOrder(
                        tuple("todoListId1", "todoListName1", LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                        tuple("todoListId2", "todoListName2", LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                );

    }

    @Test
    void should_delete_todo_list() {

        String todoListIdToDelete = "todoListId";

        ResponseEntity<Void> deleteResponse = testRestTemplate
                .withBasicAuth("testUsername", "testPassword")
                .exchange("/todo-lists/" + todoListIdToDelete, HttpMethod.DELETE, null, Void.class);

        assertThat(deleteResponse).isNotNull()
                .returns(HttpStatus.NO_CONTENT, from(ResponseEntity::getStatusCode));
    }

    @Test
    void should_retrieve_todo_list() {
        String todoListIdToRetrieve = "todoListId";

        ResponseEntity<TodoListResponse> getResponse = testRestTemplate
                .withBasicAuth("testUsername", "testPassword")
                .exchange("/todo-lists/" + todoListIdToRetrieve, HttpMethod.GET, null, TodoListResponse.class);


        assertThat(getResponse).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        assertThat(getResponse.getBody()).isNotNull()
                .returns("todoListId", from(TodoListResponse::getId))
                .returns("todoListName", from(TodoListResponse::getName))
                .returns(LocalDateTime.of(2024, 1, 1 , 0, 0, 0), from(TodoListResponse::getCreateDate));
    }

    @Test
    void should_return_unauthorized_with_wrong_credentials() {
        String todoListIdToRetrieve = "todoListId";

        ResponseEntity<TodoListResponse> getResponse = testRestTemplate
                .withBasicAuth("testUsername", "wrongPassword")
                .exchange("/todo-lists/" + todoListIdToRetrieve, HttpMethod.GET, null, TodoListResponse.class);

        assertThat(getResponse).returns(HttpStatus.UNAUTHORIZED, from(ResponseEntity::getStatusCode));
    }
}
