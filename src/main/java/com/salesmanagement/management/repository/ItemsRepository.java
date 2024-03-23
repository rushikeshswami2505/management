package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items,Long> {

    @Query("select i from Items i where i.itemType = ?1 and i.itemSize = ?2")
    List<Items> getItemsByItemTypeAndItemSize(String itemType, int itemSize);

    @Query("select i from Items i where i.itemSize = ?1")
    List<Items> getItemsByItemSize(int itemSize);

    @Query("select i from Items i where i.itemType = ?1")
    List<Items> getItemsByItemType(String itemType);
}