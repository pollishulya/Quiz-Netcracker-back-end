package com.example.repository;

import com.example.dto.GameDto;
import com.example.dto.GameFilter;
import com.example.model.Game;
//import com.example.model.QGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
//public interface GameRepository extends ExCustomRepository<Game, QGame,UUID> {
public interface GameRepository extends JpaRepository<Game, UUID> {
    Game findGameById(UUID id);
    Game findGameByTitle(String title);

    //Поиск по имени
    List<Game> findAllByTitleContaining(String title);
    //Фильтр по категории
    List<Game> findAllByGameCategory(String category);
    //Сортировка
    List<Game> findAllByOrderByViews();
    List<Game> findAllByOrderByAverageRating();
    List<Game> findAllByOrderByTitle();

//    //Dynamic Filter
//    List<Game> findByFilter(GameFilter filter);


    //querydsl


    List<Game> findGamesByPlayerId(UUID playerId);
    List<Game> findGameByAccess(String access);
}