package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "levels")
@Getter
@Setter
public class Lev implements Externalizable {
    @Id
    @GeneratedValue(generator = "levelGenerator")
    @SequenceGenerator(
            name = "levelGenerator",
            sequenceName = "levelSequence",
            initialValue = 1
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
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "level",fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lev lev = (Lev) o;

        if (id != null ? !id.equals(lev.id) : lev.id != null) return false;
        if (title != null ? !title.equals(lev.title) : lev.title != null) return false;
        return description != null ? description.equals(lev.description) : lev.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lev{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {

    }
}
