package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.entity.items.ItemsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface InwardRepository extends JpaRepository<Inward, InwardId> {

    @Query("select i from Inward i where i.inwardId.inwardItemType = ?1 and i.inwardId.inwardItemSize = ?2")
    List<Inward> getInwardByItemTypeAndItemSize(String itemType, int itemSize);

    @Query("select i from Inward i where i.inwardId.inwardItemSize = ?1")
    List<Inward> getInwardByItemSize(int itemSize);

    @Query("select i from Inward i where i.inwardId.inwardItemType = ?1")
    List<Inward> getInwardByItemType(String itemType);

    Inward findByInwardIdAndInwardDateAndInwardDozenAndInwardPiece(InwardId inwardId, String inwardDate, float inwardDozen, int inwardPiece);

    Inward findByInwardId(InwardId inwardId);

    List<Inward> findByInwardIdInwardItemSizeAndInwardIdInwardItemType(int itemSize, String itemType);

    @Modifying
    @Query("UPDATE Inward i SET i.inwardId.inwardItemSize = :newItemSize, i.inwardId.inwardItemType = :newItemType " +
            "WHERE i.inwardId.inwardItemSize = :oldItemSize AND i.inwardId.inwardItemType = :oldItemType")
    void updateInward(int oldItemSize, String oldItemType, int newItemSize, String newItemType);

    @Query("select i from Inward i where i.inwardId.inwardMemoNumber = ?1")
    List<Inward> getInwardByMemoNumber(String memoNumber);

    @Query("select i from Inward i where i.inwardId.inwardItemType = ?1 and i.inwardId.inwardMemoNumber = ?2")
    List<Inward> getInwardByItemTypeAndMemoNumber(String itemType, String memoNumber);

    @Query("select i from Inward i where i.inwardId.inwardItemSize = ?1 and i.inwardId.inwardMemoNumber = ?2")
    List<Inward> getInwardByItemSizeAndMemoNumber(int itemSize, String memoNumber);
    @Query("select i from Inward i where i.inwardId.inwardItemType = ?1 and i.inwardId.inwardItemSize = ?2 and i.inwardId.inwardMemoNumber = ?3")
    List<Inward> getInwardByItemTypeAndItemSizeAndMemoNumber(String itemType, int itemSize, String memoNumber);
}