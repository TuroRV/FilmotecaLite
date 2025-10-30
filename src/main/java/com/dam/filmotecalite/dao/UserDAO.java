package com.dam.filmotecalite.dao;

import com.dam.filmotecalite.User;

public interface UserDAO {
    void addUser();
    void updateUser();
    void deleteUser();
    User getUserByNickname(String nickname);
}
