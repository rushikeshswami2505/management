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
public class Outward {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    long outwardId;
    int outwardBailNumber;
    int outwardItemSize;
    String outwardItemType;
    String outwardDate;
    float outwardDozen;
    int outwardPiece;
}