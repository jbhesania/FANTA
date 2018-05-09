package com.example.android.projectfanta;

public class UserInfo extends User {

    private int weight;
    private int height;
    private int age;

    public UserInfo(){
        super();
    }

    public UserInfo(String id, String uname, int w, int h, int a) {
        super(id, uname);
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

    public int getHeight() {
        return height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
