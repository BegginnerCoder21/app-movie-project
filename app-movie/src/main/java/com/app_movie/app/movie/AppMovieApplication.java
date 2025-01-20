package com.app_movie.app.movie;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(

		info = @Info(
				title = "App Movie Api",
				description = "Application de visionnage de film",
				version = "v1.0",
				contact = @Contact(
						name = "Teguera Aboubacar",
						email = "aboubacarteguera21@gmail.com",
						url = "https://github.com/BegginnerCoder21/app-movie-project.git"
				),
				license = @License(
						name = "BegginnerCoder21",
						url = "https://github.com/BegginnerCoder21"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "BegginnerCoder21 Api d'application de film Documentation",
				url = "https://github.com/BegginnerCoder21/app-movie-project.git"
		)
)
public class AppMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppMovieApplication.class, args);
	}

}
