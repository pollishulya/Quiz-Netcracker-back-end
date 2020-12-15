package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "level")
@Getter
@Setter
public class Lev {
    @Id
    @GeneratedValue(generator = "level_generator")
    @SequenceGenerator(
            name = "level_generator",
            sequenceName = "level_sequence",
            initialValue = 1000
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(columnDefinition = "value")
    private String value;

    @Column(columnDefinition = "description")
    private String description;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "level",fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

}
