package com.example.myapplication.Utils;

import com.example.myapplication.DataBase.users.DataModels.DayModel;
import com.example.myapplication.DataBase.users.DataModels.FoodModel;
import com.example.myapplication.DataBase.users.DataModels.UsersIndoModel;
import com.example.myapplication.DataBase.users.DataModels.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static int index;
    private static int index1;

    private static ArrayList<Integer> foodIndexes;
    private static ArrayList<Integer> dayIndexes;

    private static List<UsersModel> users = new ArrayList<UsersModel>();
    private static List<UsersIndoModel> userInfos = new ArrayList<UsersIndoModel>();
    private static List<FoodModel> foods = new ArrayList<FoodModel>();
    private static List<DayModel> days = new ArrayList<DayModel>();

    private static String date;
    private static String username;

    private static double calories;

    private static boolean adding;
    private static boolean inserting;
    private static boolean dateChanged;
    private static boolean editing;

    private static ArrayList<String> listItems;
    private static ArrayList<String> addedItems = new ArrayList<String>();

    public static List<UsersModel> getUsers() {
        return users;
    }

    public static List<UsersIndoModel> getUserInfos() {
        return userInfos;
    }

    public static List<FoodModel> getFoods() {
        return foods;
    }

    public static List<DayModel> getDays() {
        return days;
    }

    public static void setDays(List<DayModel> days) {
        Utils.days = days;
    }

    public static void addUser(UsersModel user){
        users.add(user);
    }

    public static void addUserInfo(UsersIndoModel userInfo){
        userInfos.add(userInfo);
    }

    public static void addFood(FoodModel food){
        foods.add(food);
    }

    public static void addDay(DayModel day){
        days.add(day);
    }

    ////////////////////////////////////////////////////////////////////

    public static ArrayList<String> getListItems() {
        return listItems;
    }

    public static void setListItems(ArrayList<String> listItems) {
        Utils.listItems = listItems;
    }


    public static double getCalories() {
        return calories;
    }

    public static void setCalories(double calories) {
        Utils.calories = calories;
    }

    public static boolean isAdding() {
        return adding;
    }

    public static void setAdding(boolean adding) {
        Utils.adding = adding;
    }

    public static boolean isInserting() {
        return inserting;
    }

    public static void setInserting(boolean inserting) {
        Utils.inserting = inserting;
    }

    public static ArrayList<String> getAddedItems() {
        return addedItems;
    }

    public static void setAddedItems(ArrayList<String> addedItems) {
        Utils.addedItems = addedItems;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Utils.date = date;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Utils.username = username;
    }

    public static boolean isDateChanged() {
        return dateChanged;
    }

    public static void setDateChanged(boolean dateChanged) {
        Utils.dateChanged = dateChanged;
    }

    public static boolean isEditing() {
        return editing;
    }

    public static void setEditing(boolean editing) {
        Utils.editing = editing;
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Utils.index = index;
    }

    public static ArrayList<Integer> getDayIndexes() {
        return dayIndexes;
    }

    public static void setDayIndexes(ArrayList<Integer> dayIndexes) {
        Utils.dayIndexes = dayIndexes;
    }

    public static ArrayList<Integer> getFoodIndexes() {
        return foodIndexes;
    }

    public static void setFoodIndexes(ArrayList<Integer> foodIndexes) {
        Utils.foodIndexes = foodIndexes;
    }

    public static int getIndex1() {
        return index1;
    }

    public static void setIndex1(int index1) {
        Utils.index1 = index1;
    }
}
