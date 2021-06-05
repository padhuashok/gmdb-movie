package com.galvanize.gmdbmovie.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.domain.Rating;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import com.galvanize.gmdbmovie.repository.RatingRepository;
import com.galvanize.gmdbmovie.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.hamcrest.Matchers.is;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    MovieRepository repository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testListMovies() throws Exception {
        RequestBuilder rq = get("/movies/");
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(content().string("[]"));
    }

    @Test
    public void testCreateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Lord of The Rings");
        RequestBuilder rq = post("/movies/add").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(movie));
        this.mvc.perform(rq).
                andExpect(status().isOk()).andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    public void testMoviesExist() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Lord of The Rings");
        Movie movie2 = new Movie();
        movie2.setTitle("Home Alone");
        repository.save(movie);
        repository.save(movie2);

        RequestBuilder rq = get("/movies/");
        MvcResult result = this.mvc.perform(rq).
                andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());
        InputStream input = new FileInputStream("test-data/movies.json");
        JsonNode expected = mapper.readTree(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Lord of The Rings");
        Movie movie2 = new Movie();
        movie2.setTitle("Home Alone");
        repository.save(movie);
        repository.save(movie2);

        RequestBuilder rq = get("/movies/byTitle")
                .queryParam("title", "Lord of The Rings");
        MvcResult result = this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.title").exists()).
                andReturn();
    }
    @Test @Transactional @Rollback
    public void testGetMovieRating() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Lord of The Rings");
        repository.save(movie);


        RequestBuilder rq = patch("/movies/rating/5/id/1").
                content(asJsonString(movie));
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.rating").isArray());

        RequestBuilder rq1 = patch("/movies/rating/5/id/9").
                content(asJsonString(movie));
        this.mvc.perform(rq1).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$.message",is("Movie Does not Exist")));
    }

    @Test @Transactional @Rollback
    public void testGetAvgRating() throws Exception{
        Movie movie = new Movie();
        movie.setTitle("Lord of The Rings");
        Rating rating = new Rating();
        rating.setRating(5);

        Rating rating2 = new Rating();
        rating2.setRating(3);
        ratingRepository.save(rating);
        ratingRepository.save(rating2);
        movie.setRating(Arrays.asList(rating, rating2));
        repository.save(movie);
        System.out.println("Movie ID: " + movie.getId());
        RequestBuilder rq = get("/movies/avgrating/1").
                content(asJsonString(movie));
        this.mvc.perform(rq).andExpect(status().isOk()).
                andExpect(jsonPath("$.avgRating.avgRating",is(4)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
