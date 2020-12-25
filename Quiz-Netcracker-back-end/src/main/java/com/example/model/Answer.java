package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    private boolean right;


    public void setRight(boolean right) {
        this.right = right;
    }
}
