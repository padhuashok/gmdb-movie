package com.galvanize.gmdbmovie.domain;

import com.galvanize.gmdbmovie.dto.Rating;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @OneToOne
    private Actor director;
    private String releaseYear;
    private String description;
    @Column
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> rating = new ArrayList<>();
    @OneToMany
    private List<Actor> actors;
    @Transient
    private Rating avgRating;

    public Rating getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Rating avgRating) {
        this.avgRating = avgRating;
    }



    public Actor getDirector() {
        return director;
    }

    public void setDirector(Actor director) {
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

    public List<Integer> getRating() {
        return rating;
    }

    public void setRating(List<Integer> rating) {
        this.rating = rating;
    }
}
