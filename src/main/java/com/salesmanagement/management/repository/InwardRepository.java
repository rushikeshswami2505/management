package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.Inward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InwardRepository extends JpaRepository<Inward,Long> {

    @Query("select i from Inward i where i.inwardItemType = ?1 and i.inwardItemSize = ?2")
    List<Inward> getInwardByItemTypeAndItemSize(String itemType, int itemSize);

    @Query("select i from Inward i where i.inwardItemSize = ?1")
    List<Inward> getInwardByItemSize(int itemSize);

    @Query("select i from Inward i where i.inwardItemType = ?1")
    List<Inward> getInwardByItemType(String itemType);
}