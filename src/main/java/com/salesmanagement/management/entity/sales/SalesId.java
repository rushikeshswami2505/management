package com.salesmanagement.management.entity.sales;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SalesId implements Serializable {
    private int salesItemSize;
    private String salesItemType;
}
