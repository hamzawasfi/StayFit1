package com.example.myapplication.DataBase.users.DataModels;

public class UsersModel {

    private String username, password;
    private int goalCalories;

    public UsersModel(String username, String password, int goalCalories){
        this.username = username;
        this.password = password;
        this.goalCalories = goalCalories;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGoalCalories() {
        return goalCalories;
    }
}
