package com.stayzy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
