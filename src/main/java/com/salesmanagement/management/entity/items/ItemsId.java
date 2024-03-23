package com.salesmanagement.management.entity.items;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemsId implements Serializable {
    private int itemSize;
    private String itemType;
}
