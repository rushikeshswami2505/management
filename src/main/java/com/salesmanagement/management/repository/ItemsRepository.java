package com.salesmanagement.management.repository;

import com.salesmanagement.management.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items,Long> {
}