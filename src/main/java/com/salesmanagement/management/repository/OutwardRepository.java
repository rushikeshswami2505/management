package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.Outward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutwardRepository extends JpaRepository<Outward,Long> {
    @Query("select i from Outward i where i.outwardItemType = ?1 and i.outwardItemSize = ?2")
    List<Outward> getOutwardByItemTypeAndItemSize(String itemType, int itemSize);
}