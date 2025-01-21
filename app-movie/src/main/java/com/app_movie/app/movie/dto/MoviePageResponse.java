package com.app_movie.app.movie.dto;

import java.util.List;

public record MoviePageResponse(List<MovieRequest> movieRequests, Integer pageNumber, Integer pageSize, int totalElements, int totalPages, boolean isLast) {
}
