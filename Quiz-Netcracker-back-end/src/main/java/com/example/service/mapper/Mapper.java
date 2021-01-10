package com.example.service.mapper;

import com.example.dto.*;
import com.example.model.*;
import com.example.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper implements MapperService {

    private final QuestionService questionService;
    private final UserService userService;
    private final GameService gameService;
    private final AnswerService answerService;
    private final CategoryService categoryService;
    private final LevelService levelService;

    @Autowired
    public Mapper(QuestionService questionService, UserService userService,
                  GameService gameService, AnswerService answerService,
                  CategoryService categoryService, LevelService levelService) {
        this.questionService = questionService;
        this.userService = userService;
        this.gameService = gameService;
        this.answerService = answerService;
        this.categoryService = categoryService;
        this.levelService = levelService;
    }

    @Override
    public AnswerDto toAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setTitle(answer.getTitle());
        answerDto.setRight(answer.getRight());
        answerDto.setQuestion(answer.getQuestion().getId());
        return answerDto;
    }

    @Override
    public Answer fromAnswerDto(AnswerDto answerDto) {
        Question answerQuestion = questionService.getQuestionById(answerDto.getQuestion());
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setTitle(answerDto.getTitle());
        answer.setRight(answerDto.getRight());
        answer.setQuestion(answerQuestion);
        return answer;
    }

    @Override
    public AnswerDto toShortAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setTitle(answer.getTitle());
        answerDto.setQuestion(answer.getQuestion().getId());
        return answerDto;
    }

    @Override
    public GameDto toGameDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setDescription(game.getDescription());
        gameDto.setName(game.getName());
        gameDto.setUser(game.getUser().getId());
        return gameDto;
    }

    @Override
    public Game fromGameDto(GameDto gameDto) {
        User user = userService.getUserById(gameDto.getUser());
        Game game = new Game();
        game.setId(gameDto.getId());
        game.setDescription(gameDto.getDescription());
        game.setName(gameDto.getName());
        game.setUser(user);
        return game;
    }

    @Override
    public GameDto toShortGameDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setName(game.getName());
        gameDto.setDescription(game.getDescription());
        return gameDto;
    }

    @Override
    public LevelDto toLevelDto(Level level) {
        LevelDto levelDto = new LevelDto();
        levelDto.setId(level.getId());
        levelDto.setDescription(level.getDescription());
        levelDto.setTitle(level.getTitle());
        levelDto.setQuestions(level.getQuestions()
                .stream()
                .map(question -> toQuestionDto(question).getId())
                .collect(Collectors.toSet()));
        return levelDto;
    }

    @Override
    public Level fromLevelDto(LevelDto levelDto) {
        Set<Question> questions = levelDto.getQuestions()
                .stream()
                .map(questionService::getQuestionById)
                .collect(Collectors.toSet());
        Level level = new Level();
        level.setId(levelDto.getId());
        level.setDescription(levelDto.getTitle());
        level.setTitle(levelDto.getTitle());
        level.setQuestions(questions);
        return level;
    }

    @Override
    public LevelDto toShortLevelDto(Level level) {
        LevelDto levelDto = new LevelDto();
        levelDto.setDescription(level.getDescription());
        levelDto.setTitle(level.getTitle());
        return levelDto;
    }

    @Override
    public RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setTitle(role.getTitle());
        roleDto.setUsers(role.getUserSet()
                .stream()
                .map(user -> toUserDto(user).getId())
                .collect(Collectors.toSet()));
        return roleDto;
    }

    @Override
    public Role fromRoleDto(RoleDto roleDto) {
        Set<User> users = roleDto.getUsers()
                .stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setTitle(roleDto.getTitle());
        role.setUserSet(users);
        return role;
    }

    @Override
    public RoleDto toShortRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setTitle(role.getTitle());
        return roleDto;
    }

    @Override
    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setMail(user.getMail());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRole());
        userDto.setGames(user.getGame()
                .stream()
                .map(game -> toGameDto(game).getId())
                .collect(Collectors.toSet()));
        return userDto;
    }

    @Override
    public User fromUserDto(UserDto userDto) {
        Set<Game> games = userDto.getGames()
                .stream()
                .map(gameService::findGameById)
                .collect(Collectors.toSet());
        User user = new User();
        user.setId(userDto.getId());
        user.setMail(userDto.getMail());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getPassword());
        user.setGame(games);
        return user;
    }

    @Override
    public UserDto toShortDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setRoles(user.getRole());
        return userDto;
    }

    @Override
    public QuestionDto toQuestionDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setTitle(question.getTitle());
        questionDto.setDescription(question.getDescription());
        questionDto.setCategory(question.getCategory().getId());
        questionDto.setLevel(question.getLevel().getId());
        questionDto.setGame(question.getGame().getId());
        questionDto.setAnswersSet(question.getAnswersSet()
                .stream()
                .map(answer -> toAnswerDto(answer).getId())
                .collect(Collectors.toSet()));
        return questionDto;
    }

    @Override
    public Question fromQuestionDto(QuestionDto questionDto) {
        Set<Answer> answers = questionDto.getAnswersSet()
                .stream()
                .map(answerService::getAnswerById)
                .collect(Collectors.toSet());
        Category category = categoryService.findCategoryById(questionDto.getCategory());
        Level level = levelService.findLevelById(questionDto.getLevel());
        Game game = gameService.findGameById(questionDto.getGame());
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTitle(questionDto.getTitle());
        question.setDescription(questionDto.getDescription());
        question.setCategory(category);
        question.setLevel(level);
        question.setGame(game);
        question.setAnswersSet(answers);
        return question;
    }

    @Override
    public QuestionDto toShortQuestionDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setTitle(question.getTitle());
        questionDto.setDescription(question.getDescription());
        questionDto.setCategory(question.getCategory().getId());
        questionDto.setLevel(question.getLevel().getId());
        questionDto.setAnswersSet(question.getAnswersSet()
                .stream()
                .map(answer -> toAnswerDto(answer).getId())
                .collect(Collectors.toSet()));
        return questionDto;
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(category.getId());
    categoryDto.setName(category.getName());
    categoryDto.setDescription(category.getDescription());
    categoryDto.setQuestion(category.getQuestion()
            .stream()
            .map(question -> toQuestionDto(question).getId())
            .collect(Collectors.toSet()));
    return categoryDto;
    }

    @Override
    public Category fromCategoryDro(CategoryDto categoryDto) {
        Set<Question> questions = categoryDto.getQuestion()
                .stream()
                .map(questionService::getQuestionById)
                .collect(Collectors.toSet());
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setQuestion(questions);
        return category;
    }

    @Override
    public CategoryDto toShortCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public PlayerDto toPlayerDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setGames(player.getGame()
                .stream()
                .map(game -> toGameDto(game).getId())
                .collect(Collectors.toSet()));
        return playerDto;
    }

    @Override
    public Player fromPlayerDto(PlayerDto playerDto) {
        Set<Game> games = playerDto.getGames()
                .stream()
                .map(gameService::findGameById)
                .collect(Collectors.toSet());
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setGame(games);
        return player;
    }
}


