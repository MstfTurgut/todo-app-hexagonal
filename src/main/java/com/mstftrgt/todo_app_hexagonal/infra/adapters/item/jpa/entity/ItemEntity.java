package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa.entity;

import com.mstftrgt.todo_app_hexagonal.domain.item.model.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "items")
public class ItemEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @JoinColumn(name = "todo_list_id")
    private String todoListId;


    public Item toModel() {
        return Item.builder()
                .id(id)
                .name(name)
                .status(status)
                .todoListId(todoListId)
                .deadline(deadline)
                .createDate(createDate)
                .build();
    }
}
