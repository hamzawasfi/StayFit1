package com.example.myapplication.DataBase.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.DataBase.users.DataModels.FoodModel;
import com.example.myapplication.Utils.Variables;


public class FoodDataBase extends DataBase {

    private static int foodCalories;

    public FoodDataBase(@Nullable Context context) {
        super(context);
    }

    public static int getFoodCalories() {
        return foodCalories;
    }

    private FoodModel foodModel;

    public boolean insertData(String foodName, int calories){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL10_FOOD_NAME, foodName);
        contentValues.put(COL11_FOOD_CAL, calories);
        contentValues.put(COL12_USERNAME, Variables.getUsername());
        long result = db.insert(FOOD_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertGlobalData(String foodName, int calories){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL10_FOOD_NAME, foodName);
        contentValues.put(COL11_FOOD_CAL, calories);
        contentValues.put(COL12_USERNAME, "global");
        long result = db.insert(FOOD_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {COL9_FOOD_ID, COL10_FOOD_NAME, COL11_FOOD_CAL, COL12_USERNAME};
        Cursor cursor = db.query(FOOD_TABLE_NAME, data, null, null, null, null, null);
        return cursor;
    }

    public Cursor getUserLastData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE_NAME + " WHERE " + COL12_USERNAME + " = ?" + " ORDER BY " + COL9_FOOD_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[] {username});
        return cursor;
    }

    public void addFoodModel(){
        Cursor cursor = getData();

        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                foodModel = new FoodModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
                Variables.addFood(foodModel);
            }
        }
    }

    //get on create

    public Cursor getUserData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {COL9_FOOD_ID, COL10_FOOD_NAME, COL11_FOOD_CAL, COL12_USERNAME};
        String selection = COL12_USERNAME + " LIKE ?";
        String[] selection_args = {username};
        Cursor cursor = db.query(FOOD_TABLE_NAME, data, selection, selection_args, null, null, null);
        return cursor;
    }

    //

    //get on resume

    public Cursor getInsertedData(String foodID, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE_NAME + " WHERE " + COL10_FOOD_NAME + " = ? and " + COL12_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {foodID, username});
        return cursor;
    }

    //

    //global get

    public Cursor getDataByName(String foodName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE_NAME + " WHERE " + COL10_FOOD_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{foodName});
        return cursor;
    }

    //

    //convert to int and string

    public double getFoodCalories(String foodName){
        double calories = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor  = getDataByName(foodName);
        if(cursor.moveToFirst()){
            calories = Integer.parseInt(cursor.getString(2));
        }
        foodCalories = (int)calories;
        return calories;
    }

    public String getStringFoodID(String foodName){
        String ID = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor  = getDataByName(foodName);
        if(cursor.moveToFirst()){
            ID = cursor.getString(0);
        }
        return ID;
    }

    //
}
