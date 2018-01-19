package org.superbiz.moviefun.moviesapi;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class MoviesClient {

    private final RestOperations restOperations;
    private final String moviesUrl;
    public static String EMPTY="";
    public static String movieFunServiceError=EMPTY;
    
    /*@Autowired
    private EurekaClient discoveryClient;*/
    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };
    public MoviesClient(String moviesUrl , RestOperations restOperations){
        this.moviesUrl=moviesUrl;
        this.restOperations=restOperations;
    }
  /*  private String fetchServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(moviesUrl, false);
        
        String serviceUrl = instance.getHomePageUrl();
        System.out.println("Web Service URL"+serviceUrl);
        return serviceUrl+"/movies";
    }*/
    @HystrixCommand(fallbackMethod = "failAddMovie")
    public void addMovie(MovieInfo movie) {

    	System.out.println("Inside Add Movie start");
    	restOperations.postForEntity(moviesUrl, movie,MovieInfo.class);
    	System.out.println("Inside Add Movie end");
    }
    public void failAddMovie() {
    	System.out.println("Add Movie Service Failed. Try another. Inside Fallback start");
    	movieFunServiceError="Add Movie Service Failed. Try another.";
    	
    }
//    @HystrixCommand(fallbackMethod = "failDeleteMovieId")
    public void deleteMovieId(long movieId) {
        restOperations.delete(moviesUrl + "/" + movieId);
    }
    public String failDeleteMovieId() {
    	return "Delete Movie Service Failed. Try another.";

    }
//    @HystrixCommand(fallbackMethod = "failCountAll")
    public int countAll() {
        return restOperations.getForObject(moviesUrl + "/count", Integer.class);
    }
    public String failCountAll() {
    	return "Count All Movie Service Failed. Try another.";

    }
//    @HystrixCommand(fallbackMethod = "failCount")
    public int count(String field, String key) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
        .queryParam("field", field)
        .queryParam("key", key);
        return restOperations.getForObject(builder.toUriString(), Integer.class);

    }
    public String failCount() {
    	return "Count Movie Service Failed. Try another.";

    }
//    @HystrixCommand(fallbackMethod = "failFindAll")
    public List<MovieInfo> findAll(int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }
    public String failFindAll() {
    	return "Find All Movie Service Failed. Try another.";

    }
//    @HystrixCommand(fallbackMethod = "failFindRange")
    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), HttpMethod.GET, null, movieListType).getBody();
    }
    public List<MovieInfo> failFindRange() {
    	System.out.println("Find All Movie Service Failed. Try another.");
    	return null;

    }
//    @HystrixCommand(fallbackMethod = "failGetMovies")
    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, HttpMethod.GET, null, movieListType).getBody();
    }
    public String failGetMovies() {
    	return "Find All Movie Service Failed. Try another.";

    }
}
