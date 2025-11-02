package com.dam.filmotecalite.dao;

import com.dam.filmotecalite.Movie;
import com.dam.filmotecalite.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class MovieDAOImpl implements MovieDAO {

    @Override
    public ArrayList<Movie> getUserMovies(int userId) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT movies.* FROM movies INNER JOIN users_movies ON movie_id = users_movies.id_movie WHERE users_movies.id_user = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getString(6));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getAllMovies() {


    }

    @Override
    public int addMovie(Movie movie) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO movies (movie_title,movie_genre,movie_director,movie_duration,movie_agerating) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setString(3, movie.getDirector());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setString(5, movie.getRating());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el movie " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void updateMovie(Movie movie) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movies SET movie_title = ?, movie_genre = ?, movie_director = ?, movie_duration = ?, movie_agerating = ? WHERE movie_id = ?");
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setString(3, movie.getDirector());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setString(5, movie.getRating());
            preparedStatement.setInt(6, movie.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteMovie(Movie movie, int userId) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "DELETE FROM users_movies WHERE id_movie = ? AND id_user= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movie.getId());
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Movie getMovieByTitle(String title) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM movies WHERE movie_title = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Movie (
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)
                );
            }


        } catch (SQLException e) {
            System.out.println("Error al buscar la película por título" + title);
        }
        return null;
    }

    @Override
    public void addMovieToUser(int userId, int movieId) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users_movies (id_user,id_movie) VALUES (?,?)");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, movieId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
