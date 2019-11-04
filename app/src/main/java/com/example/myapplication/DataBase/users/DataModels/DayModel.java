package com.example.myapplication.DataBase.users.DataModels;

public class DayModel {

    private int ID, foodID, foodCal, foodWeight;
    private String username, foodName, date;

    public DayModel(int ID, String username, int foodID, String foodName, int foodCal, int foodWeight, String date){
        this.ID = ID;
        this.username = username;
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodCal = foodCal;
        this.foodWeight = foodWeight;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public int getFoodID() {
        return foodID;
    }

    public int getFoodCal() {
        return foodCal;
    }

    public int getFoodWeight() {
        return foodWeight;
    }

    public String getUsername() {
        return username;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getDate() {
        return date;
    }
}
