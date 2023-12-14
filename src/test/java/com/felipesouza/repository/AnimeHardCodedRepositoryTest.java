package com.felipesouza.repository;

import com.felipesouza.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var onePiece = Anime.builder().id(2L).name("One Piece").build();
        var dragonBallZ = Anime.builder().id(3L).name("Dragon Ball Z").build();

        animes = new ArrayList<>(List.of(naruto, onePiece, dragonBallZ));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @DisplayName("findAll() return a list with all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        var animes = repository.findAll();
        Assertions.assertThat(animes).hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findById() returns an anime filtered by id")
    @Order(2)
    void findById_ReturnAnime_WhenSuccessful() {
        var animeOptional = repository.findById(1L);
        Assertions.assertThat(animeOptional).isPresent().contains(this.animes.getFirst());
    }

    @Test
    @DisplayName("findByName() return all animes when name is null")
    @Order(3)
    void findByName_ReturnsAllAnimes_WhenNameIsNull() {
        var animes = repository.findByName(null);
        Assertions.assertThat(animes).hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("findByName() returns animes when name is not null")
    @Order(4)
    void findByName_ReturnAnime_WhenNameIsNotNull() {
        var anime = repository.findByName("Naruto");
        Assertions.assertThat(anime).hasSize(1).contains(this.animes.getFirst());
    }

    @Test
    @DisplayName("findByName() return an empty list when name is not found")
    @Order(5)
    void findByName_ReturnEmptyList_WhenNameIsNotFound() {
        var anime = repository.findByName("XXXX");
        Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() create an anime")
    @Order(6)
    void save_CreateAnime_WhenSuccessful() {
        var animeToSave = Anime.builder().id(99L).name("Death Note").build();

        var anime = repository.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave).hasNoNullFieldsOrProperties();

        var animes = repository.findAll();

        Assertions.assertThat(animes).contains(animeToSave);
    }

    @Test
    @DisplayName("delete() removes an anime")
    @Order(7)
    void delete_RemoveAnime_WhenSuccessful() {
        var animeToDelete = repository.findByName("Naruto").getFirst();
        repository.delete(animeToDelete);

        Assertions.assertThat(this.animes).doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update() update an anime")
    @Order(8)
    void update_UpdateAnime_WhenSuccessful() {
        var animeToUpdate = repository.findByName("Naruto").getFirst();
        animeToUpdate.setName("Death Note");
        repository.update(animeToUpdate);

        this.animes
                .stream()
                .filter(a -> a.getId().equals(animeToUpdate.getId()))
                .findFirst()
                .ifPresent(a -> Assertions.assertThat(animeToUpdate).isEqualTo(a));
    }
}
