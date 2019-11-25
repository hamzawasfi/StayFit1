package com.example.myapplication.DataBase.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.DataBase.users.DataModels.UsersIndoModel;
import com.example.myapplication.Utils.Variables;

public class UsersInfoDataBase extends DataBase{

    private UsersIndoModel usersInfoModel;

    public UsersInfoDataBase(@Nullable Context context) {
        super(context);
    }

    public boolean insertData(int age, int weight, int height, int sex, int bodyGoal, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4_AGE, age);
        contentValues.put(COL5_WEIGHT, weight);
        contentValues.put(COL6_HEIGHT, height);
        contentValues.put(COL7_SEX, sex);
        contentValues.put(COL8_BODY_GOAL, bodyGoal);
        contentValues.put(COL20_USERNAME, username);
        long result = db.insert(USERS_DATA_TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public void addUserInfoModel(){
        Cursor cursor = getData();

        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                usersInfoModel = new UsersIndoModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6));
                Variables.addUserInfo(usersInfoModel);
            }
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] data = {COL21_ROW_ID, COL4_AGE, COL5_WEIGHT, COL6_HEIGHT, COL7_SEX, COL8_BODY_GOAL, COL20_USERNAME};
        Cursor cursor = db.query(USERS_DATA_TABLE_NAME, data, null, null, null, null, null);
        return cursor;
    }
}
