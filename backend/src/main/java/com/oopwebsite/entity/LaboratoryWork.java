package com.oopwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String pathToFile;

    private int mark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id")
    @JsonBackReference
    private User owner;
}
