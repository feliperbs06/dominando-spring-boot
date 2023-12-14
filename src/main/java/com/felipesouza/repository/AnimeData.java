package com.felipesouza.repository;

import com.felipesouza.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class AnimeData {
    private final List<Anime> animes = new ArrayList<>();

    {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Konosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr.Stone").build();
        animes.addAll(List.of(jigokuraku, konosuba, drStone));
    }
}
