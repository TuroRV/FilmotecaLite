package com.dam.filmotecalite.dao;

import com.dam.filmotecalite.User;
import com.dam.filmotecalite.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser() {

    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public User getUserByNickname(String nickname) {
        Connection connection = DatabaseConnection.getConnection();
        User user;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE nickname = ?");
            ResultSet resultSet = preparedStatement.executeQuery();
            user = null;
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_surname"),
                        resultSet.getString("user_nickname"),
                        resultSet.getString("user_email"),
                        resultSet.getDate("user_birthdate"),
                        resultSet.getBoolean("user_isadmin"),
                        resultSet.getString("user_password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean authenticate(String user_nickname, String user_password) {

        User user = getUserByNickname(user_nickname);

        if (user.getUser_nickname().equals(user_nickname) && user.getUser_password().equals(user_password)) {
            return true;
        }
        else
        return false;
    }
}
