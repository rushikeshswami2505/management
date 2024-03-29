package com.salesmanagement.management.entity.inward;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inward {
    @EmbeddedId
    private InwardId inwardId;

    private String inwardDate;
    private float inwardDozen;
    private int inwardPiece;
}

