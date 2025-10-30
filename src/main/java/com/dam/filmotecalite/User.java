package com.dam.filmotecalite;

import java.util.Date;

public class User {

    private int user_id;
    private String user_name;
    private String user_surname;
    private String user_nickname;
    private String user_email;
    private Date user_birthdate;
    private boolean user_isadmin;
    private String user_password;

    public User(int user_id, String user_name, String user_surname, String user_nickname, String user_email, Date user_birthdate, boolean user_isadmin, String user_password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_nickname = user_nickname;
        this.user_email = user_email;
        this.user_birthdate = user_birthdate;
        this.user_isadmin = user_isadmin;
        this.user_password = user_password;
    }

    public User(String user_name, String user_surname, String user_nickname, String user_email, Date user_birthdate, boolean user_isadmin, String user_password) {
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_nickname = user_nickname;
        this.user_email = user_email;
        this.user_birthdate = user_birthdate;
        this.user_isadmin = user_isadmin;
        this.user_password = user_password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Date getUser_birthdate() {
        return user_birthdate;
    }

    public void setUser_birthdate(Date user_birthdate) {
        this.user_birthdate = user_birthdate;
    }

    public boolean isUser_isadmin() {
        return user_isadmin;
    }

    public void setUser_isadmin(boolean user_isadmin) {
        this.user_isadmin = user_isadmin;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
