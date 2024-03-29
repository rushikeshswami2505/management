package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.sales.Sales;
import com.salesmanagement.management.entity.sales.SalesId;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SalesRepository extends JpaRepository<Sales,SalesId> {

}
