package com.example.model;

import com.example.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(generator = "role_generator")
    @SequenceGenerator(
            name = "role_generator",
            sequenceName = "role_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_to_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet = new HashSet<>();

}