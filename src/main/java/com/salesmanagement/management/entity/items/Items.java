package com.salesmanagement.management.entity.items;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @EmbeddedId
    private ItemsId itemsId;
}