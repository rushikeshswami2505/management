package com.salesmanagement.management.entity.inward;


import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InwardId implements Serializable {
    private String inwardMemoNumber;
    private int inwardItemSize;
    private String inwardItemType;
}