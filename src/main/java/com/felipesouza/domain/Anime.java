package com.felipesouza.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Anime {

    private Long id;
    @JsonProperty(value = "name")
    private String name;

    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        var jigokuraku = new Anime(1L, "Jigokuraku");
        var konosuba = new Anime(2L, "Konosuba");
        var drStone = new Anime(1L, "Dr.Stone");
        animes.addAll(List.of(jigokuraku, konosuba, drStone));
    }

}
