package com.shair13.external_service.mapper;

import com.shair13.external_service.dto.ReadMovieDto;
import com.shair13.external_service.dto.WriteMovieDto;
import com.shair13.external_service.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie toDomain(WriteMovieDto writeMovieDto);

    ReadMovieDto toReadDto(Movie movie);
}
