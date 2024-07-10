package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest;

import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.UseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.common.usecase.VoidUseCaseHandler;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListCreate;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListDelete;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieve;
import com.mstftrgt.todo_app_hexagonal.domain.todolist.usecase.TodoListRetrieveAll;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest.dto.TodoListResponse;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.rest.dto.NewTodoListRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("todo-lists")
public class TodoListController {

    private final VoidUseCaseHandler<TodoListDelete> todoListDeleteVoidUseCaseHandler;
    private final UseCaseHandler<TodoList, TodoListCreate> todoListCreateUseCaseHandler;
    private final UseCaseHandler<TodoList, TodoListRetrieve> todoListRetrieveUseCaseHandler;
    private final UseCaseHandler<List<TodoList>, TodoListRetrieveAll> todoListRetrieveAllUseCaseHandler;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TodoListResponse> getAllTodoLists() {
        TodoListRetrieveAll useCase = new TodoListRetrieveAll(getCurrentUserId());
        return todoListRetrieveAllUseCaseHandler.handle(useCase).stream().map(TodoListResponse::from).toList();
    }

    @DeleteMapping("{todoListId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTodoList(@PathVariable String todoListId) {
        TodoListDelete useCase = new TodoListDelete(todoListId, getCurrentUserId());
        todoListDeleteVoidUseCaseHandler.handle(useCase);
    }

    @GetMapping("/{todoListId}")
    @ResponseStatus(code = HttpStatus.OK)
    public TodoListResponse getTodoListById(@PathVariable String todoListId) {
        TodoListRetrieve useCase = new TodoListRetrieve(todoListId, getCurrentUserId());
        return TodoListResponse.from(todoListRetrieveUseCaseHandler.handle(useCase));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TodoListResponse createTodoList(@RequestBody @Valid NewTodoListRequest todoListRequest, UriComponentsBuilder ucb, HttpServletResponse response) {
        TodoListCreate useCase = todoListRequest.toUseCase(getCurrentUserId());
        TodoList todoList = todoListCreateUseCaseHandler.handle(useCase);
        setLocationHeader(ucb, response, todoList.getId());
        return TodoListResponse.from(todoList);
    }

    private void setLocationHeader(UriComponentsBuilder ucb, HttpServletResponse response, String todoListId) {
        URI locationOfNewTodoList = ucb.path("todo-lists/{todoListId}").buildAndExpand(todoListId).toUri();
        response.setHeader("Location", locationOfNewTodoList.toString());
    }

    private String getCurrentUserId() {
        UserEntity userEntity = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntity.getId();
    }
}

