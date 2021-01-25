package com.example.controller;

import com.example.dto.UserDto;
import com.example.model.Player;
import com.example.model.RoleList;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.security.LoginModel;
import com.example.service.interfaces.UserService;
import com.example.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    @Autowired
    public UserController(UserService userService, UserMapper mapper, PlayerRepository playerRepository) {
        this.userService = userService;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/findAllUsers")
    public List<UserDto> getUsers() {
        return userService.findAllUser().stream().map(mapper:: toDto).collect(Collectors.toList());
    }

    @GetMapping("/findUser/{userId}")
    public UserDto getUsers(@PathVariable UUID userId) {
        return mapper.toDto(userService.getUserById(userId));
    }


    @PostMapping("/save")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User user= mapper.toEntity(userDto);
        return mapper.toDto(userService.saveUser(user));
    }

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

    @RequestMapping(value = "/loginA", method = { RequestMethod.POST, RequestMethod.GET})
    public User loginAccount(@RequestBody LoginModel loginModel) {
        User userFacade = new User(loginModel.getUsername(),
                loginModel.getLogin(),
                bCryptPasswordEncoder.encode(loginModel.getPassword())
        );
        return userService.login(userFacade);
    }


    @PostMapping("/register")
    UUID singUp(@RequestBody LoginModel loginModel){
        User userFacade = new User(loginModel.getUsername(),
                loginModel.getLogin(),
                bCryptPasswordEncoder.encode(loginModel.getPassword())
        );
        userFacade.setRole(RoleList.USER);

      //  userFacade.setPlayer(player);
        userService.saveUser(userFacade);

        return userFacade.getId();
    }


    @PostMapping("/registerPlayer")
    UUID singUp(@RequestBody Player player){
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        // user.setStatus(true);
//        //  user.setActive(1);   //TODO Make front validation depending on active
//        user.setRole(role);
        User user = setUser(player.getUser(), RoleList.USER);  //TODO check on presence in DB
        userService.saveUser(user);
        playerRepository.save(player);
       // sendRegistrationEmail(account, client.getEmail());
        return user.getId();
    }
    private User setUser(User user, String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // user.setStatus(true);
        //  user.setActive(1);   //TODO Make front validation depending on active
        user.setRole(role);

        return user;
    }
}