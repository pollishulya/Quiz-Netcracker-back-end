package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    User findUserById(UUID id);
    User findUserByLogin(String login);
    User findByLoginOrMail(String login, String mail);
    User findUserByActivationCode(String code);
    User findByLoginAndPassword(String username, String password);
}
