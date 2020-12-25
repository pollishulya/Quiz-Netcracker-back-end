package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")

    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> userSet = new HashSet<>();

    private Role(String title) {
        this.title = title;
    }

    public static Role USER = new Role("ROLE_USER");
    public static Role ADMIN = new Role("ROLE_ADMIN");
    public static Role GUEST = new Role("ROLE_MANAGER");

    @Builder
    public Role(UUID id, String title, Set<User> userSet) {
        this.id = id;
        this.title = title;
        this.userSet = userSet;
    }
}


