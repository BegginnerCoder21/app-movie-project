package com.app_movie.app.movie.utils;

import java.util.Random;

public class AuthUtils {

    public static Integer generateOtp()
    {
        Random random = new Random();
        return random.nextInt(100_000, 900_000);
    }
}
