package com.galvanize.gmdbmovie.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.FileInputStream;
import java.io.InputStream;

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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
