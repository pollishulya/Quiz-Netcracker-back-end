package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "levels")
@Data
public class Level {
    @Id
    @GeneratedValue(generator = "levelGenerator")
    @SequenceGenerator(
            name = "levelGenerator",
            sequenceName = "levelSequence"
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "level",fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();
}
