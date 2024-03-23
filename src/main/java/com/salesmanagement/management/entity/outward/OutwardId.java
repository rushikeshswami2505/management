package com.salesmanagement.management.entity.outward;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OutwardId implements Serializable {
    private int outwardBailNumber;
    private int outwardItemSize;
    private String outwardItemType;
}