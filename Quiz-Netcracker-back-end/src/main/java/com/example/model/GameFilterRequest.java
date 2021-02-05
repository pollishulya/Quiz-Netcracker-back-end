package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameFilterRequest {


    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Long views;

    @Getter
    @Setter
    private Long averageRating;

    @Getter
    @Setter
    private Long ratingCount;

    @Getter
    @Setter
    private GameCategory gameCategory;


//    public GameFilterRequest ( String name, String description,
//                              Long views, Long averageRating, Long ratingCount, GameCategory gameCategory) {
//        this.title = name;
//        this.description = description;
//        this.views = views;
//        this.averageRating = averageRating;
//        this.ratingCount = ratingCount;
//        this.gameCategory = gameCategory;
//    }


}
