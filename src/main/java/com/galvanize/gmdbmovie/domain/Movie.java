package com.galvanize.gmdbmovie.domain;

import com.galvanize.gmdbmovie.dto.RatingDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToOne
    private User director;
    private String releaseYear;
    private String description;
    @OneToMany
    private List<Rating> rating = new ArrayList<>();
    @OneToMany
    private List<Actor> actors;
    @Transient
    private RatingDto avgRating;

    public RatingDto getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(RatingDto avgRating) {
        this.avgRating = avgRating;
    }



    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Movie() {

    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }
}
