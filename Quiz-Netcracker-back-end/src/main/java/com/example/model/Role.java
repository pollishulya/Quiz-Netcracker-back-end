package com.example.model;

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
@Table(name = "roles")
@Getter
@Setter
public class Role implements Externalizable {
    @Id
    @GeneratedValue(generator = "role_generator")
    @SequenceGenerator(
            name = "role_generator",
            sequenceName = "role_sequence"
    )
    private Long id;

    @Column(name = "title")
    @Enumerated(EnumType.STRING)
    private com.example.enums.Roles title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> userSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (id != null ? !id.equals(role1.id) : role1.id != null) return false;
        if (title != null ? !title.equals(role1.title) : role1.title != null) return false;
        return userSet != null ? userSet.equals(role1.userSet) : role1.userSet == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (userSet != null ? userSet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title=" + title +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {

    }
}