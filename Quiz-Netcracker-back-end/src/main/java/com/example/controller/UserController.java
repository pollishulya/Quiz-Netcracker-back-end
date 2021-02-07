package com.example.controller;

import com.example.dto.ActivateCodeDto;
import com.example.dto.UserDto;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.InvalidActivationCodeException;
import com.example.exception.InvalidEmailException;
import com.example.exception.QuizBaseException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.exception.detail.ExceptionDetails;
import com.example.model.RoleList;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.security.LoginModel;
import com.example.service.impl.AmazonClient;
import com.example.service.interfaces.GameAccessService;
import com.example.service.interfaces.UserService;
import com.example.service.mapper.UserMapper;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.example.service.validation.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private final CustomValidator customValidator;
    private final MessageSource messageSource;


    @Autowired
    public UserController(UserService userService, UserMapper mapper, PlayerRepository playerRepository, AmazonClient amazonClient,
                          GameAccessService gameAccessService, CustomValidator customValidator, MessageSource messageSource) {
        this.userService = userService;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
        this.amazonClient = amazonClient;
        this.gameAccessService = gameAccessService;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.customValidator = customValidator;
        this.messageSource = messageSource;
    }

    @GetMapping("/findAllUsers")
    public List<UserDto> getUsers() {
        return userService.findAllUser().stream().map(mapper::toShortDto).collect(Collectors.toList());
    }

    @GetMapping("/checkAllUsers")
    public List<UserDto> checkUsers() {
        return userService.findAllUser().stream().map(mapper:: toShortDto).collect(Collectors.toList());
    }

    @GetMapping("/findUser/{userId}")
    public UserDto getUsers(@PathVariable UUID userId) {
        return mapper.toDto(userService.getUserById(userId));
    }

    @PutMapping("/update/{userId}")
    public UserDto updateUser(@PathVariable UUID userId,
                              @RequestBody UserDto userDto) {
        Map<String, String> propertyViolation = customValidator.validate(userDto, Update.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.ARGUMENT_NOT_VALID, propertyViolation, messageSource);
        }
        User user = mapper.toEntity(userDto);
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
            throw new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                    messageSource.getMessage("message.ResourceNotFound", new Object[]{ null }, LocaleContextHolder.getLocale()));
        } else {
            return mapper.toDto(userService.findUserByUsername(username));
        }
    }

    @PostMapping("/register")
    User singUp(@RequestBody LoginModel loginModel/*, HttpServletRequest request*/) {
        Map<String, String> propertyViolation = customValidator.validate(loginModel, Create.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.ARGUMENT_NOT_VALID, propertyViolation, messageSource);
        }
        User userFacade = new User(loginModel.getUsername(),
                loginModel.getMail(),
                bCryptPasswordEncoder.encode(loginModel.getPassword())
        );
        userFacade.setRole(RoleList.ADMIN);
        userService.saveUser(userFacade/*,request.getLocalAddr())*/);
        User user = userService.findUserByUsername(userFacade.getLogin());
        gameAccessService.createGameAccessByPlayer(user.getId());
        return userFacade;
    }

    @GetMapping("/activate/{code}")
    public ActivateCodeDto activate(Model model, @PathVariable String code) {
        ActivateCodeDto dto = new ActivateCodeDto();
        dto.setText(userService.activateUser(code) ? "Ваша активация прошла успешно!" : "Код активации неверный!");
        return dto;
    }

    @PostMapping(value = "/block/{userId}")
    public UserDto blockUser(@PathVariable UUID userId) {
        return mapper.toDto(userService.blockUser(userId));
    }
}