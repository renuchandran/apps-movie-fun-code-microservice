package org.superbiz.moviefun.moviesapi;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MoviesClient moviesClient;
    private final MovieFixtures movieFixtures;

    public HomeController(MoviesClient moviesClient,
                          MovieFixtures movieFixtures) {
        this.moviesClient = moviesClient;
        this.movieFixtures = movieFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
    	try{
        for (MovieInfo movie : movieFixtures.load()) {
            moviesClient.addMovie(movie);
        }
    	}catch(Exception exception){
        	System.out.println(exception.getMessage());
        }
        model.put("movies", moviesClient.getMovies());
        return "setup";
    }
}
