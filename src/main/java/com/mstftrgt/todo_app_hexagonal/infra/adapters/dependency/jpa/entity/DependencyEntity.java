package com.mstftrgt.todo_app_hexagonal.infra.adapters.dependency.jpa.entity;

import com.mstftrgt.todo_app_hexagonal.domain.dependency.model.Dependency;
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

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dependencies")
public class DependencyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(name = "item_id")
    private String itemId;

    @JoinColumn(name = "dependent_item_id")
    private String dependentItemId;

    public Dependency toModel() {
        return Dependency.builder()
                .id(id)
                .itemId(itemId)
                .dependentItemId(dependentItemId)
                .build();
    }
}
