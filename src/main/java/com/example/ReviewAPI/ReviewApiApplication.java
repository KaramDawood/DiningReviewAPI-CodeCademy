package com.example.ReviewAPI;

import com.example.ReviewAPI.model.Restaurant;
import com.example.ReviewAPI.model.Review;
import com.example.ReviewAPI.model.ReviewStatus;
import com.example.ReviewAPI.model.UserD;
import com.example.ReviewAPI.repository.RestaurantRepository;
import com.example.ReviewAPI.repository.ReviewRepository;
import com.example.ReviewAPI.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReviewApiApplication {

	public static void main(String[] args) {
				SpringApplication.run(ReviewApiApplication.class, args);
	}


	@Bean
	CommandLineRunner init(RestaurantRepository repository, UserRepository userRepository, ReviewRepository reviewRepository) {
		return args -> {
			repository.save(new Restaurant(null, "Locanda Würzburg - Pizza & Pasta", "Kranenkai 1", "0931 15600",
					"97070", "Würzburg", "Bavaria", "wuerzburg@locanda.de", 5.7, 6.0, 3.4, 14.64));

			repository.save(new Restaurant(null, "Domino's Pizza", "Randersackerer Str. 47", "0931 7847333",
					"97072", "Würzburg", "Bavaria", "info@dominos.de",  null, 6.0, 3.4,13.58));

			repository.save(new Restaurant(null, "Ristorante Le Candele", "Spiegelstraße 10", "0931 47085004",
					"97070", "Würzburg", "Bavaria", "risto@wue.com",  null, 6.0, 3.4, 10.56));

			repository.save(new Restaurant(null, "Blub", "Spiegelstraße 10", "0931 47085004",
					"97070", "Würzburg", "Bavaria", "dominos@wue.com",  null, 6.0, 3.4,11.54));

			repository.save(new Restaurant(null, "Babbaa", "Spiegelstraße 10", "0931 47085004",
					"97078", "Würzburg", "Bavaria", "dominos@wue.com",  null, 6.0, 3.4,13.64));

			userRepository.save(new UserD(null, "John Stewart", "Würzburg", "Bavaria",
					"97438", false, false, true));

			userRepository.save(new UserD(null, "Kari Duikon", "Magdeburg", "Sachsen-Anhalt",
					"39112", true, true, true));

			userRepository.save(new UserD(null, "Dora Martanala", "Hamburg", "Hamburg",
					"20099", false, true, true));

		reviewRepository.save(new Review(null, "Kari Dukon", 3L, 4.5, 3.56, 5.32, ReviewStatus.PENDING, "nice Restaurant!"));

		};
	}

}
