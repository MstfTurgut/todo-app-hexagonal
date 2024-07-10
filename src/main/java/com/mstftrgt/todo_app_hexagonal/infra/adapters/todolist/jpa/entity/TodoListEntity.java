package com.mstftrgt.todo_app_hexagonal.infra.adapters.todolist.jpa.entity;

import com.mstftrgt.todo_app_hexagonal.domain.todolist.model.TodoList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_lists")
public class TodoListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "user_id")
    private String userId;

    public TodoList toModel() {
        return TodoList.builder()
                .id(id)
                .name(name)
                .userId(userId)
                .createDate(createDate)
                .build();
    }

}
