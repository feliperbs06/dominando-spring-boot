package com.felipesouza.mapper;

import com.felipesouza.domain.Producer;
import com.felipesouza.request.ProducerPostRequest;
import com.felipesouza.request.ProducerPutRequest;
import com.felipesouza.response.ProducerGetResponse;
import com.felipesouza.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest request);

    @Mapping(source = "createdAt", target = "createdAt")
    Producer toProducer(ProducerPutRequest request, LocalDateTime createdAt);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponses(List<Producer> producers);
}
