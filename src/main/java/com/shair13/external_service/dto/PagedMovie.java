package com.shair13.external_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagedMovie {
    private List<ReadMovieDto> movies;
    PageDetails pageDetails;
}
