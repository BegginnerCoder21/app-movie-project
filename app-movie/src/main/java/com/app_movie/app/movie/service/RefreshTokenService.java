package com.app_movie.app.movie.service;

import com.app_movie.app.movie.entity.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken creatingRefreshToken(String username);
    public RefreshToken verifyRefreshToken(String refreshToken);

}
