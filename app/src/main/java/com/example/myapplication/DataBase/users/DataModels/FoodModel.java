package com.example.myapplication.DataBase.users.DataModels;

public class FoodModel {

    private int ID, foodCal;
    private String foodName, username;

    public FoodModel(int ID, String foodName, int foodCal, String username){
        this.ID = ID;
        this.foodName = foodName;
        this.foodCal = foodCal;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public int getFoodCal() {
        return foodCal;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getUsername() {
        return username;
    }
}
