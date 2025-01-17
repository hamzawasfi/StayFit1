package com.example.myapplication.DataBase.users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";

    public static final String USERS_TABLE_NAME = "users_table";
    public static final String COL1_USERNAME = "username";
    public static final String COL2_PASSWORD = "password";
    public static final String COL3_USER_GOAL_CAL = "goal_calories";

    public static final String USERS_DATA_TABLE_NAME = "users_data_table";
    public static final String COL21_ROW_ID = "ID";
    public static final String COL4_AGE = "age";
    public static final String COL5_WEIGHT = "weight";
    public static final String COL6_HEIGHT = "height";
    public static final String COL7_SEX = "sex";
    public static final String COL8_BODY_GOAL = "body_goal";
    public static final String COL20_USERNAME = "username";

    public static final String FOOD_TABLE_NAME = "food_table";
    public static final String COL9_FOOD_ID = "food_ID";
    public static final String COL10_FOOD_NAME = "name";
    public static final String COL11_FOOD_CAL = "calories";
    public static final String COL12_USERNAME = "username";

    public static final String DAY_TABLE_NAME = "day_table";
    public static final String COL13_ROW_ID = "ID";
    public static final String COL14_USERNAME = "username";
    public static final String COL15_FOOD_ID = "food_ID";
    public static final String COL16_FOOD_NAME = "food_name";
    public static final String COL17_FOOD_CAL = "food_calories";
    public static final String COL18_FOOD_WEIGHT = "food_weight";
    public static final String COL19_DATE = "date";

    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USERS_TABLE_NAME + " (" + COL1_USERNAME + " TEXT PRIMARY KEY, " + COL2_PASSWORD + " TEXT, " + COL3_USER_GOAL_CAL + " INTEGER " + ") ");
        db.execSQL("create table " + USERS_DATA_TABLE_NAME + " (" + COL21_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL4_AGE + " INTEGER, " + COL5_WEIGHT + " INTEGER, " + COL6_HEIGHT + " INTEGER, " + COL7_SEX + " INTEGER, " + COL8_BODY_GOAL + " INTEGER, " + COL20_USERNAME + " TEXT " + ") ");
        db.execSQL("create table " + FOOD_TABLE_NAME + " (" + COL9_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL10_FOOD_NAME + " TEXT, " + COL11_FOOD_CAL + " INTEGER, " + COL12_USERNAME + " TEXT " + ")");
        db.execSQL("create table " + DAY_TABLE_NAME + " (" + COL13_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL14_USERNAME + " TEXT, " + COL15_FOOD_ID + " INTEGER, " + COL16_FOOD_NAME + " TEXT, " + COL17_FOOD_CAL + " INTEGER, " + COL18_FOOD_WEIGHT + " INTEGER, " + COL19_DATE + " TEXT " + ")");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_DATA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAY_TABLE_NAME);
        onCreate(db);
    }
}
