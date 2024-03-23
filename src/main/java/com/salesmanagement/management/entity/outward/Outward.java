package com.salesmanagement.management.entity.outward;
import com.salesmanagement.management.entity.inward.InwardId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Outward {

    @EmbeddedId
    private OutwardId outwardId;

    private String outwardDate;
    private float outwardDozen;
    private int outwardPiece;
}