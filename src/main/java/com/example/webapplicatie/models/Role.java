package com.example.webapplicatie.models;

import javax.persistence.*;

@Entity
// Rollen entiteit
@Table(name = "roles")
public class Role {

    // Defineert kolommen in rollen tabel
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
    }

    // contructor
    public Role(ERole name) {
        this.name = name;
    }

    // Getters en setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
