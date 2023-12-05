package com.felipesouza.domain;

import java.util.List;

public class Anime {

    private Long id;
    private String name;

    public Anime(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static final List<Anime> animes = List.of(
            new Anime("Naruto", 1L),
            new Anime("Death Note", 2L),
            new Anime("Boruto", 3L),
            new Anime("Dragonball Z", 4L)
    );

    public static List<Anime> getAnimes() {
        return animes;
    }
}
