package com.felipesouza.service;

import com.felipesouza.domain.Anime;
import com.felipesouza.repository.AnimeHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {
    private AnimeHardCodedRepository repository;

    public AnimeService() {
        this.repository = new AnimeHardCodedRepository();
    }

    public List<Anime> findAll(String name) {
        return repository.findByName(name);
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public Anime findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public void delete(Long id) {
        var producer = findById(id);
        repository.delete(producer);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate);
        repository.update(animeToUpdate);
    }

    public void assertAnimeExists(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
    }
}
