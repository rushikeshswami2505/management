package com.salesmanagement.management.repository;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.outward.OutwardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutwardRepository extends JpaRepository<Outward, OutwardId> {
    @Query("select i from Outward i where i.outwardId.outwardItemType = ?1 and i.outwardId.outwardItemSize = ?2")
    List<Outward> getOutwardByItemTypeAndItemSize(String itemType, int itemSize);

    @Query("select i from Outward i where i.outwardId.outwardItemSize = ?1")
    List<Outward> getOutwardByItemSize(int itemSize);

    @Query("select i from Outward i where i.outwardId.outwardItemType = ?1")
    List<Outward> getOutwardByItemType(String itemType);

    Outward findByOutwardIdAndOutwardDateAndOutwardDozenAndOutwardPiece(OutwardId outwardId, String outwardDate, float outwardDozen, int outwardPiece);

    Outward findByOutwardId(OutwardId outwardId);

    @Modifying
    @Query("UPDATE Outward o SET o.outwardId.outwardItemSize = :newItemSize, o.outwardId.outwardItemType = :newItemType " +
            "WHERE o.outwardId.outwardItemSize = :oldItemSize AND o.outwardId.outwardItemType = :oldItemType")
    void updateOutward(int oldItemSize, String oldItemType, int newItemSize, String newItemType);

    //@Query("select i from Outward i where i.outwardId.outwardBailNumber = ?1")
    @Query("select i from Outward i where i.outwardId.outwardBailNumber = ?1")
    List<Outward> getOutwardByBailNumber(String bailNumber);

    @Query("select i from Outward i where i.outwardId.outwardItemType = ?1 and i.outwardId.outwardBailNumber = ?2")
    List<Outward> getOutwardByItemTypeAndBailNumber(String itemType, String bailNumber);

    @Query("select i from Outward i where i.outwardId.outwardItemSize = ?1 and i.outwardId.outwardBailNumber = ?2")
    List<Outward> getOutwardByItemSizeAndBailNumber(int itemSize, String bailNumber);

    @Query("select i from Outward i where i.outwardId.outwardItemType = ?1 and i.outwardId.outwardItemSize = ?2 and i.outwardId.outwardBailNumber = ?3")
    List<Outward> getOutwardByItemTypeAndItemSizeAndBailNumber(String itemType, int itemSize, String bailNumber);
}