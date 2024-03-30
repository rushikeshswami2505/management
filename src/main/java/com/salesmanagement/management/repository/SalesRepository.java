package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.sales.Sales;
import com.salesmanagement.management.entity.sales.SalesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales,SalesId> {

    @Query("select i from Sales i where i.salesId.salesItemSize = ?1")
    List<Sales> getSalesByItemSize(int itemSize);

    @Query("select i from Sales i where i.salesId.salesItemType = ?1")
    List<Sales> getSalesByItemType(String itemType);

    @Query("select i from Sales i where i.salesId.salesItemType = ?1 and i.salesId.salesItemSize = ?2")
    List<Sales> getSalesByItemTypeAndItemSize(String itemType, int itemSize);
}
