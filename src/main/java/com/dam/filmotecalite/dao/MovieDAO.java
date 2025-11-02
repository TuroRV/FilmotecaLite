package com.dam.filmotecalite.dao;

import com.dam.filmotecalite.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MovieDAO {
    ArrayList<Movie> getUserMovies(int userId);
    void getAllMovies();
    int addMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(Movie movie,int userId);
    Movie getMovieByTitle(String title);
    void addMovieToUser(int userId, int  movieId);
}
