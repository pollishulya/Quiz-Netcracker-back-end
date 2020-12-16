package com.example.model;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answers")
public class Answer extends AuditModel implements Externalizable {
    @Id
    @GeneratedValue(generator = "answerGenerator")
    @SequenceGenerator(
            name = "answerGenerator",
            sequenceName = "answerSequence"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "answerIsRight")
    private boolean right;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "question_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Question question;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "questionAnswer",
            joinColumns = @JoinColumn(columnDefinition= "answerId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "questionId"))
    private Set<Question> questionsSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (right != answer.right) return false;
        if (id != null ? !id.equals(answer.id) : answer.id != null) return false;
        return title != null ? title.equals(answer.title) : answer.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (right ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", right=" + right +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {

    }
}
