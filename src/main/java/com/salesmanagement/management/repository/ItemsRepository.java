package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.ItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, ItemsId> {

    @Query("select i from Items i where i.itemsId.itemType = ?1 and i.itemsId.itemSize = ?2")
    List<Items> getItemsByItemTypeAndItemSize(String itemType, int itemSize);

    @Query("select i from Items i where i.itemsId.itemSize = ?1")
    List<Items> getItemsByItemSize(int itemSize);

    @Query("select i from Items i where i.itemsId.itemType = ?1")
    List<Items> getItemsByItemType(String itemType);

    Items findByItemsId(ItemsId itemsId);
}