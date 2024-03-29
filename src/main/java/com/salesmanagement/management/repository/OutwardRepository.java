package com.salesmanagement.management.repository;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.outward.OutwardId;
import org.springframework.data.jpa.repository.JpaRepository;
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
}