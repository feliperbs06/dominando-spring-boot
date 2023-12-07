package com.felipesouza.controller;

import com.felipesouza.domain.Anime;
import com.felipesouza.mapper.AnimeMapper;
import com.felipesouza.request.AnimePostRequest;
import com.felipesouza.response.AnimeGetResponse;
import com.felipesouza.response.AnimePostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        var response = MAPPER.toAnimeGetResponses(animes);
        if (name == null) return ResponseEntity.ok(response);
        response = response.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> filterById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var anime = Anime.getAnimes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
        var response = MAPPER.toGetResponse(anime);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> saveAnime(@RequestBody AnimePostRequest request) {
        log.info("Request received save anime'{}'", request);
        var anime = MAPPER.toAnime(request);
        var response = MAPPER.toAnimePostResponse(anime);

        Anime.getAnimes().add(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
