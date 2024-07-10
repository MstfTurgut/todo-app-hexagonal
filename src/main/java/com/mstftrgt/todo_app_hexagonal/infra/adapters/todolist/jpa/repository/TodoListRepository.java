package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.repository;

import com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.entity.TodoListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository extends JpaRepository<TodoListEntity, String> {

    List<TodoListEntity> findByUserId(String userId);

    Optional<TodoListEntity> findByNameAndUserId(String name, String userId);
}
