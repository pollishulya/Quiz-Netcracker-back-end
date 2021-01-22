package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Player;
import com.example.model.RoleList;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.repository.UserRepository;
import com.example.security.LoginModel;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
        Player player= new Player(user.getMail(),user.getLogin(),user);        user.setPlayer(player);
        userRepository.save(user);
        playerRepository.save(player);
        return  user;
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

//    @Override
//    public Player registerPlayer(LoginModel player) {
//      //  User user = setUser(player.getUser(), RoleList.USER);  //TODO check on presence in DB
//        //user.setPlayer(player);
//       // userRepository.save(user);
//        //playerRepository.save(player);
//       // sendRegistrationEmail(account, client.getEmail());
//        return player;
//    }

    private User setUser(User user, String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       // user.setStatus(true);
      //  user.setActive(1);   //TODO Make front validation depending on active
        user.setRole(role);

        return user;
    }
}
