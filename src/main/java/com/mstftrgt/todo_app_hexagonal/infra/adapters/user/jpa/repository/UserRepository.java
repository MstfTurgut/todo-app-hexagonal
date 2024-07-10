package com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.repository;

import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);
}
