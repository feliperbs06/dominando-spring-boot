package com.felipesouza.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;

    public static List<Anime> getAnimes() {
        return List.of(
                new Anime(1L, "Naruto"),
                new Anime(2L, "Death Note"),
                new Anime(3L, "Boruto"),
                new Anime(4L, "Dragonball Z")
        );
    }

}
