package com.felipesouza.service;

import com.felipesouza.domain.Anime;
import com.felipesouza.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodedRepository repository;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var onePiece = Anime.builder().id(2L).name("One Piece").build();
        var dragonBallZ = Anime.builder().id(3L).name("Dragon Ball Z").build();

        animes = new ArrayList<>(List.of(naruto, onePiece, dragonBallZ));
    }

    @Test
    @DisplayName("findAll() return a list with all animes when name is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenNameIsNull() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.animes);

        var animes  = service.findAll(null);
        Assertions.assertThat(animes).hasSameElementsAs(this.animes);

    }

    @Test
    @DisplayName("findAll() return a list with found animes when name is not null")
    @Order(2)
    void findAll_ReturnAnime_WhenSuccessful() {
        var name = "Naruto";
        var animesFound = this.animes.stream().filter(anime -> anime.getName().equals(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(animesFound);

        var animes  = service.findAll(name);
        Assertions.assertThat(animes).hasSameElementsAs(animesFound);

    }

    @Test
    @DisplayName("findAll() return an empty list when no anime is found by name")
    @Order(3)
    void findAll_ReturnEmptyList_WhenNameNotFound() {
        var name = "xxx";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var animes  = service.findAll(name);
        Assertions.assertThat(animes).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("findById() return an anime when id is found")
    @Order(4)
    void findById_ReturnAnime_WhenSuccessful() {
        var id = 1L;
        var anime = this.animes.getFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(anime));

        Assertions.assertThatNoException().isThrownBy(() -> service.findById(id));

    }

    @Test
    @DisplayName("findById() throw ResponseStatusException when no producer is found")
    @Order(5)
    void findById_ReturnEmptyList_WhenIdNotFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save() create anime")
    @Order(6)
    void save_CreateAnime_WhenSuccessful() {
        var animeToCreate = Anime.builder()
                .id(99L)
                .name("Death Note")
                .build();

        BDDMockito.when(repository.save(animeToCreate)).thenReturn(animeToCreate);

        var animeCreated = service.save(animeToCreate);

        Assertions.assertThat(animeCreated).isEqualTo(animeToCreate).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete() removes anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessful() {
        var id = 1L;
        var animeToDelete = this.animes.getFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));

    }

    @Test
    @DisplayName("delete() throw ResponseStatusException when id is not found")
    @Order(8)
    void delete_ThrowResponseStatusException_WhenIdNotFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);

    }


    @Test
    @DisplayName("update() update anime")
    @Order(9)
    void update_UpdateAnime_WhenSuccessful() {
        var id = 1L;
        var animeToUpdate = this.animes.getFirst();
        animeToUpdate.setName("Death Note");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.doNothing().when(repository).update(animeToUpdate);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @DisplayName("update() thrown ResponseStatusException when id is not found")
    @Order(10)
    void update_ThrowResponseStatusException_WhenIdNotFound() {
        var id = 1L;
        var animeToUpdate = this.animes.getFirst();
        animeToUpdate.setName("Death Note");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }




}