package com.app_movie.app.movie.dto;

import java.util.List;


public record MoviePageResponse(List<MovieResponse> MovieResponses, Integer pageNumber, Integer pageSize, long totalElements, int totalPages, boolean isLast) {
}
