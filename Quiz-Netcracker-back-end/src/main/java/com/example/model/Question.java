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
@Table(name = "questions")
@Getter
@Setter
public class Question extends AuditModel implements Externalizable {
    @Id
    @GeneratedValue(generator = "questionGenerator")
    @SequenceGenerator(
            name = "questionGenerator",
            sequenceName = "questionSequence"
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelId")
    private Lev level;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "questionAnswer",
            joinColumns = @JoinColumn(columnDefinition = "questionId"),
            inverseJoinColumns = @JoinColumn(columnDefinition  = "answerId"))
    private Set<Answer> answersSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "questionGame",
            joinColumns = @JoinColumn(columnDefinition = "questionId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "gameId"))
    private Set<Game> gamesSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) return false;
        if (title!= null ? !title.equals(question.title) : question.title != null) return false;
        if (category != null ? !category.equals(question.category) : question.category != null) return false;
        return level != null ? level.equals(question.level) : question.level == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", level=" + level +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
