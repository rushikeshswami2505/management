package com.salesmanagement.management.entity.items;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SearchItems {

    private int itemSize;
    private String itemType;
    private String memoNumber;
    private String BailNumber;
}
