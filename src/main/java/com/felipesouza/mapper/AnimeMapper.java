package com.felipesouza.mapper;

import com.felipesouza.domain.Anime;
import com.felipesouza.request.AnimePostRequest;
import com.felipesouza.request.AnimePutRequest;
import com.felipesouza.response.AnimeGetResponse;
import com.felipesouza.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnime(AnimePostRequest request);

    Anime toAnime(AnimePutRequest request);
    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponses(List<Anime> animes);

}
