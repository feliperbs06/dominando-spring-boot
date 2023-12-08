package com.felipesouza.controller;

import com.felipesouza.mapper.AnimeMapper;
import com.felipesouza.request.AnimePostRequest;
import com.felipesouza.request.AnimePutRequest;
import com.felipesouza.response.AnimeGetResponse;
import com.felipesouza.response.AnimePostResponse;
import com.felipesouza.service.AnimeService;
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
    private AnimeService animeService;

    public AnimeController() {
        this.animeService = new AnimeService();
    }

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name) {
        var animes = animeService.findAll(name);
        var response = MAPPER.toAnimeGetResponses(animes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> filterById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var anime = animeService.findById(id);
        var response = MAPPER.toGetResponse(anime);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> saveAnime(@RequestBody AnimePostRequest request) {
        log.info("Request received save anime'{}'", request);
        var anime = MAPPER.toAnime(request);
        anime = animeService.save(anime);
        var response = MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the anime by id '{}'", id);
        animeService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.info("Request received to update the anime '{}'", request);
        var animeToUpdate = MAPPER.toAnime(request);
        animeService.update(animeToUpdate);
        return ResponseEntity.noContent().build();
    }
}
