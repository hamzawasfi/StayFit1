package com.example.myapplication.DataBase.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.DataBase.users.DataModels.DayModel;
import com.example.myapplication.Utils.Variables;

public class DaysDataBase extends DataBase {

    private DayModel daysModel;

    public DaysDataBase(@Nullable Context context) {
        super(context);
    }

    //insert on adding

    public boolean insertData(String username, String foodID, String foodName, double calories, int weight, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL14_USERNAME, username);
        contentValues.put(COL15_FOOD_ID, foodID);
        contentValues.put(COL16_FOOD_NAME, foodName);
        contentValues.put(COL17_FOOD_CAL, (int)calories);
        contentValues.put(COL18_FOOD_WEIGHT, weight);
        contentValues.put(COL19_DATE, date);
        long result = db.insert(DAY_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public void deleteRow(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DAY_TABLE_NAME + " WHERE " + COL13_ROW_ID + " = ? ", new String[] {ID});
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {COL13_ROW_ID, COL14_USERNAME, COL15_FOOD_ID, COL16_FOOD_NAME, COL17_FOOD_CAL, COL18_FOOD_WEIGHT, COL19_DATE};
        Cursor cursor = db.query(DAY_TABLE_NAME, data, null, null, null, null, null);
        return cursor;
    }

    public Cursor getUserLastData(String username, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_TABLE_NAME + " WHERE " + COL14_USERNAME + " = ?" + " AND " + COL19_DATE + " = ?" + " ORDER BY " + COL13_ROW_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[] {username, date});
        return cursor;
    }

    public void addDayModel(){
        Cursor cursor = getData();

        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                daysModel = new DayModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6));
                Variables.addDay(daysModel);
            }
        }
    }
}
