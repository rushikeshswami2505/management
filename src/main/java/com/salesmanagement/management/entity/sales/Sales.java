package com.salesmanagement.management.entity.sales;

import com.salesmanagement.management.entity.outward.OutwardId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
    @EmbeddedId
    private SalesId salesId;
    private float salesDozen;
    private int salesPiece;
}
