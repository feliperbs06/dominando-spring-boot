package com.felipesouza.controller;


import com.felipesouza.mapper.ProducerMapper;
import com.felipesouza.request.ProducerPostRequest;
import com.felipesouza.request.ProducerPutRequest;
import com.felipesouza.response.ProducerGetResponse;
import com.felipesouza.response.ProducerPostResponse;
import com.felipesouza.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/producers", "v1/producers/"})
@Log4j2
@RequiredArgsConstructor
public class ProducerController {
    private ProducerMapper mapper;
    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {
        var producers = producerService.findAll(name);
        var response = mapper.toProducerGetResponses(producers);
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = mapper.toProducer(request);
        producer = producerService.save(producer);
        var response = mapper.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the producer by id '{}'", id);
        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.info("Request received to update the producer by '{}'", request);
        var producerToUpdate = mapper.toProducer(request);
        producerService.update(producerToUpdate);
        return ResponseEntity.noContent().build();
    }
}
