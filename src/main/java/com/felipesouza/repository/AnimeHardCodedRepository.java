package com.felipesouza.repository;

import com.felipesouza.domain.Anime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class AnimeHardCodedRepository {
    private final AnimeData animeData;

    public List<Anime> findAll() {
        return animeData.getAnimes();
    }


    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst();
    }

    public List<Anime> findByName(String name) {
        return name == null ? animeData.getAnimes() :
                animeData.getAnimes()
                        .stream()
                        .filter(producer -> producer.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        animeData.getAnimes().remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }

}
