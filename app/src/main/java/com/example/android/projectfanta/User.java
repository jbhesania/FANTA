package com.example.android.projectfanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String id;
    private String userName;

    public User() {

    }

    public User(String id, String uname) {
        this.id = id;
        this.userName = uname;
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
