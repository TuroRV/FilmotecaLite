package com.dam.filmotecalite.dao;

import com.dam.filmotecalite.Movie;

import java.util.ArrayList;

public interface MovieDAO {
    ArrayList<Movie> getUserMovies(int userId);
    ArrayList<Movie> getAllMovies();
    int addMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovieFromFavorites(Movie movie, int userId);
    Movie getMovieByTitle(String title);
    void addMovieToUser(int userId, int  movieId);
    void deleteMovieFromDatabase(Movie movie);
    void updateMovieFromDatabase(Movie movie);
}
