package com.oopwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String path;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id")
    @JsonBackReference
    private User creator;
}
