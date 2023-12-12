package com.felipesouza.repository;

import com.felipesouza.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProducerHardCodedRepositoryTest {
    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        var mappa = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producers = List.of(mappa, kyotoAnimation, madhouse);

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll should return a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSize(3);
    }

}