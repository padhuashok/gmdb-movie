package com.galvanize.gmdbmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

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
                andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
