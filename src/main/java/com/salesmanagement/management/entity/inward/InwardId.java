package com.salesmanagement.management.entity.inward;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InwardId implements Serializable {
    private int inwardMemoNumber;
    private int inwardItemSize;
    private String inwardItemType;
}