package com.example.service.impl;

import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Player;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.repository.UserRepository;
import com.example.service.interfaces.GameAccessService;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final GameAccessService gameAccessService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailSender mailSender;
    private final MessageSource messageSource;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PlayerRepository playerRepository, GameAccessService gameAccessService, MessageSource messageSource, MailSender mailSender) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.gameAccessService = gameAccessService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user/*, String urlAddress*/) {
        User userFromDb = userRepository.findByLoginOrMail(user.getLogin(), user.getMail());
        if (userFromDb != null) {
            return null;
        } else {
            Player player = new Player(user.getMail(), user.getLogin(), user);
             user.setActive(false); //оставить, когда будет активация через почту
         //   user.setActive(true);//убрать, когда будет активация через почту
            user.setActivationCode(UUID.randomUUID().toString());
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to localhost. Please, visit next link: http://localhost:8085/users/activate/%s",
                    user.getLogin(),
                    // urlAddress,
                    user.getActivationCode()
            );
            mailSender.send(user.getMail(), "Activation code", message);
            userRepository.save(user);
            playerRepository.save(player);

            return user;
        }
    }

    @Override
    public User updateUser(UUID userId, User userRequest) {
        UUID[] args = new UUID[]{ userId };
        return userRepository.findById(userId).map(user -> {
            user.setLogin(userRequest.getLogin());
            user.setPassword(userRequest.getPassword());
            user.setMail(userRequest.getMail());
            user.setRole(userRequest.getRole());
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public void deleteUser(UUID userId) {
        try {
            userRepository.deleteById(userId);
        }
        catch (RuntimeException exception) {
            UUID[] args = new UUID[]{ userId };
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public User getUserById(UUID userId) {
        UUID[] args = new UUID[]{ userId };
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByLoginOrMail(username, username);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public User blockUser(UUID userId) {
        User userToBlock = userRepository.findUserById(userId);
        if(userToBlock.isActive()) {
            userToBlock.setActive(false);
        }
        else{
            userToBlock.setActive(true);
        }
        return userRepository.save(userToBlock);
    }

}
