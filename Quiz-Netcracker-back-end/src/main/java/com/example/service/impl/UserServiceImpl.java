package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
//        User userFromDB = userRepository.findUserByLogin(user.getLogin());
//        if (userFromDB != null) {
//            return false;
//        }
//        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    @Override
    public User updateUser(UUID userId, User userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setLogin(userRequest.getLogin());
            user.setPassword(userRequest.getPassword());
            user.setMail(userRequest.getMail());
            user.setRole(userRequest.getRole());
//            if (userRequest.getGame() != null) {
//                user.getGame().clear();
//                user.getGame().addAll(userRequest.getGame());
//            }
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(UUID userId){
        return userRepository.findUserById(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByLogin(username);
    }

//    @Override
//    public User confirmAccount(String token) {
//        VerificationToken passedToken = verificationTokenRepository.findByToken(token);
//        User account = userRepository.findUserByLogin(passedToken.getUser().getLogin());
//       // account.setActive(1);
//        userRepository.save(account);
//        return account;
//    }

    @Override
    public User login(User account) {
        return userRepository.findByLoginAndPassword(account.getLogin(),
                account.getPassword());
    }
}
