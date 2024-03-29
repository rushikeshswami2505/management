package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}