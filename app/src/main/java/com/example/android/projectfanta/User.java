package com.example.android.projectfanta;

public class User {

    private String id;
    private String userName;

    public User() {

    }

    public User(String id, String uname) {
        this.id = id;
        userName = uname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
