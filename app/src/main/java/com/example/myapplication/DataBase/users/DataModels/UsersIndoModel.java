package com.example.myapplication.DataBase.users.DataModels;

public class UsersIndoModel {


    private int ID, age, weight, height, sex, bodyGoal;
    private String username;

    public UsersIndoModel(int ID, int age, int weight, int height, int sex, int bodyGoal, String username){
        this.ID = ID;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.bodyGoal = bodyGoal;
        this.username = username;
    }

    public int getID() {
        return ID;
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

    public int getSex() {
        return sex;
    }

    public int getBodyGoal() {
        return bodyGoal;
    }

    public String getUsername() {
        return username;
    }

}
