package com.salesmanagement.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inward {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    long inwardId;
    int inwardMemoNumber;
    int inwardItemSize;
    String inwardItemType;
    String inwardDate;
    float inwardDozen;
    int inwardPiece;
}
