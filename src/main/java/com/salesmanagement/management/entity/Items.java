package com.salesmanagement.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(Items.ItemCompositeKey.class)
public class Items {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;
    private int itemSize;
    private String itemType;

//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class ItemCompositeKey implements Serializable {
//        private long itemId;
//        private int itemSize;
//        private String itemType;
//    }
}