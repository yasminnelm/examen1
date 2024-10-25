package com.example.backend.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private CD cd;

    @Temporal(TemporalType.DATE)
    private Date borrowDate;


    public Emprunt() {}

    public Emprunt(User user, CD cd) {
        this.user = user;
        this.cd = cd;
        this.borrowDate = new Date();
    }


}

