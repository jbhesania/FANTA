package com.example.android.projectfanta;

public class User {

    private String id;
    private int weight;
    private int height;
    private int age;
    //private int

    public User(){

    }

    public User(String id, int w, int h, int a) {
        this.id = id;
        weight = w;
        height = h;
        age = a;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
