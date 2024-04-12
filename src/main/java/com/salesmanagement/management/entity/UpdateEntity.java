package com.salesmanagement.management.entity;

import com.salesmanagement.management.entity.items.Items;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEntity {
    private Items itemsOld;
    private Items itemsNew;
}