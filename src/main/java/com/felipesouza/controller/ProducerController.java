package com.felipesouza.controller;


import com.felipesouza.mapper.ProducerMapper;
import com.felipesouza.domain.Producer;
import com.felipesouza.request.ProducerPostRequest;
import com.felipesouza.request.ProducerPutRequest;
import com.felipesouza.response.ProducerGetResponse;
import com.felipesouza.response.ProducerPostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        var response = MAPPER.toProducerGetResponses(producers);
        if (name == null) return ResponseEntity.ok(response);
        response = response.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = MAPPER.toProducer(request);
        var response = MAPPER.toProducerPostResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the producer by id '{}'", id);
        var producer = Producer.getProducers()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be deleted"));
        Producer.getProducers().remove(producer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.info("Request received to update the producer by '{}'", request);
        var producer = Producer.getProducers()
                .stream()
                .filter(p -> p.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be updated"));
        var producerUpdated = MAPPER.toProducer(request, producer.getCreatedAt());
        Producer.getProducers().remove(producer);
        Producer.getProducers().add(producerUpdated);
        return ResponseEntity.noContent().build();
    }
}
