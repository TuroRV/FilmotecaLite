package com.dam.filmotecalite;

public class Session {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User u) {
        user = u;
    }

}
