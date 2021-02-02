package com.example.controller;

import com.example.dto.UserDto;
import com.example.model.*;
import com.example.repository.PlayerRepository;
import com.example.security.LoginModel;
import com.example.service.impl.AmazonClient;
import com.example.service.interfaces.GameAccessService;
import com.example.service.interfaces.UserService;
import com.example.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PlayerRepository playerRepository;
    private final AmazonClient amazonClient;
    private final GameAccessService gameAccessService;


    @Autowired
    public UserController(UserService userService, UserMapper mapper, PlayerRepository playerRepository, AmazonClient amazonClient, GameAccessService gameAccessService) {
        this.userService = userService;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
        this.amazonClient = amazonClient;
        this.gameAccessService = gameAccessService;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/findAllUsers")
    public List<UserDto> getUsers() {
        return userService.findAllUser().stream().map(mapper:: toShortDto).collect(Collectors.toList());
    }

    @GetMapping("/findUser/{userId}")
    public UserDto getUsers(@PathVariable UUID userId) {
        return mapper.toDto(userService.getUserById(userId));
    }


//    @PostMapping("/save")
//    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
//        User user= mapper.toEntity(userDto);
//        return mapper.toDto(userService.saveUser(user));
//    }

    @PutMapping("/update/{userId}")
    public UserDto updateUser(@PathVariable UUID userId,
                                   @Valid @RequestBody UserDto userDto) {
        User user= mapper.toEntity(userDto);
        return mapper.toDto(userService.updateUser(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public UserDto getOneAccount(@PathVariable String username) {
            return mapper.toDto(userService.findUserByUsername(username));
    }

    @GetMapping("/check/{username}")
    public UserDto checkAccount(@PathVariable String username) {
        User userFromDb = userService.findUserByUsername(username);
        if (userFromDb == null) {
            return null;}
        else{
            return mapper.toDto(userService.findUserByUsername(username));
        }
    }

    @PostMapping("/register")
    UUID singUp(@RequestBody LoginModel loginModel/*, HttpServletRequest request*/){
        User userFacade = new User(loginModel.getUsername(),
                loginModel.getMail(),
                bCryptPasswordEncoder.encode(loginModel.getPassword())
        );
        userFacade.setRole(RoleList.ADMIN);
        userService.saveUser(userFacade/*,request.getLocalAddr())*/);
        User user= userService.findUserByUsername(userFacade.getLogin());
        gameAccessService.createGameAccessByPlayer(user.getId());
        return userFacade.getId();
    }


    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "Ваша активация прошла успешно");
        } else {
            model.addAttribute("message", "Код активации не найден!");
        }
        model.addAttribute("isActivated", isActivated);
        return "Ваша активация прошла успешно";
    }

    @PostMapping(value = "/block/{userId}")
    public UserDto blockUser(@PathVariable UUID userId) {
        return mapper.toDto(userService.blockUser(userId));
    }
}