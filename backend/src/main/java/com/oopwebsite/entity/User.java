package com.oopwebsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@EqualsAndHashCode(of = "id")
@Data
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String password;

    private String name;

    private String email;


    @Column(unique = true)
    private String login;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;


    @Enumerated(EnumType.STRING)
    private Group groupName;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private Collection<LaboratoryWork> laboratoryWorks = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @JsonManagedReference
    private Collection<Lecture> lectureList = new ArrayList<>();


}
