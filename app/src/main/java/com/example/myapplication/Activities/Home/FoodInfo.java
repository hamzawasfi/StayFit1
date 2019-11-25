package com.example.myapplication.Activities.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBase.users.DataModels.DayModel;
import com.example.myapplication.DataBase.users.DaysDataBase;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Variables;

import java.util.ArrayList;
import java.util.List;

public class FoodInfo extends AppCompatActivity {

    private TextView title, calories;
    private EditText weight;
    private Button done;

    private DaysDataBase daysDB;

    private DayModel dayModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        daysDB = new DaysDataBase(this);

        String foodName = Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getFoodName();
        String foodCal = String.valueOf(Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getFoodCal());
        String foodWeight = String.valueOf(Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getFoodWeight());

        title = (TextView)findViewById(R.id.titletxt);
        title.setText(foodName);

        calories = (TextView)findViewById(R.id.caloriestxt1);
        calories.setText(foodCal);

        weight = (EditText)findViewById(R.id.food_weighttxt);
        weight.setText(foodWeight);
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        done = (Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double newWeight = Integer.parseInt(weight.getText().toString());
                double newCalories = Variables.getFoods().get(Variables.getIndex1()).getFoodCal() / 100 * newWeight;

                removeAddedItem();

                boolean d = daysDB.insertData(Variables.getUsername(), String.valueOf(Variables.getFoods().get(Variables.getIndex1()).getID()), Variables.getFoods().get(Variables.getIndex1()).getFoodName(), newCalories, (int)newWeight, Variables.getDate());
                addDayModel();
                if(!d){
                    Toast.makeText(getApplicationContext(), "food info.insertdata", Toast.LENGTH_SHORT).show();
                }
                Variables.setCalories(newCalories);
                Variables.setEditing(true);
                finish();
            }
        });
    }

    public ArrayList<String> removeAddedItem(ArrayList<String> list){
        daysDB.deleteRow(String.valueOf(Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getID()));
        list.remove(Variables.getIndex());
        Variables.getDays().remove(Variables.getDayIndexes().get(Variables.getIndex()));
        Variables.setAddedItems(list);
        return list;
    }

    public void addDayModel(){
        Cursor cursor = daysDB.getUserLastData(Variables.getUsername(), Variables.getDate());

        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                dayModel = new DayModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6));
                Variables.addDay(dayModel);
            }
        }
    }

    public void removeAddedItem(){
        List<DayModel> newModel;
        daysDB.deleteRow(String.valueOf(Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getID()));
        int x = Variables.getDayIndexes().get(Variables.getIndex());
        newModel = Variables.getDays();
        newModel.remove(x);
        Variables.setDays(newModel);
    }
}
