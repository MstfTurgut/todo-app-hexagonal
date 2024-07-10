package com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa.repository;

import com.mstftrgt.todo_app_hexagonal.infra.adapters.item.jpa.entity.ItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, String> {

    List<ItemEntity> findByTodoListId(String todoListId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update items set status = ?1 where id = ?2")
    void updateItemStatusByItemId(boolean status, String itemId);

    void deleteAllByTodoListId(String todoListId);
}
