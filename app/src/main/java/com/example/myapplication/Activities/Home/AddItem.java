package com.example.myapplication.Activities.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBase.users.DataModels.DayModel;
import com.example.myapplication.DataBase.users.DaysDataBase;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Variables;

public class AddItem extends AppCompatActivity {

    private TextView itemName, warning;
    private EditText itemWeight;
    private Button addItem;

    private DaysDataBase daysDB;

    private DayModel dayModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        daysDB = new DaysDataBase(this);

        warning = (TextView)findViewById(R.id.warning_add);

        itemName = (TextView)findViewById(R.id.item_name);
        Toast.makeText(getApplicationContext(), Variables.getIndex() + " ", Toast.LENGTH_SHORT).show();
        itemName.setText(Variables.getFoods().get(Variables.getFoodIndexes().get(Variables.getIndex())).getFoodName());

        itemWeight = (EditText)findViewById(R.id.item_weight);
        itemWeight.setInputType(InputType.TYPE_CLASS_NUMBER);

        addItem = (Button)findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()) {
                    double calories = Variables.getFoods().get(Variables.getFoodIndexes().get(Variables.getIndex())).getFoodCal();
                    double weight = Integer.parseInt(itemWeight.getText().toString());
                    double foodCalories = calories / 100 * weight;

                    boolean d = daysDB.insertData(Variables.getUsername(), String.valueOf(Variables.getFoods().get(Variables.getFoodIndexes().get(Variables.getIndex())).getID()), Variables.getFoods().get(Variables.getFoodIndexes().get(Variables.getIndex())).getFoodName(), foodCalories, (int)weight, Variables.getDate());
                    addDayModel();
                    if(!d){
                        Toast.makeText(getApplicationContext(), "add item.insertdata", Toast.LENGTH_SHORT).show();
                    }

                    Variables.setCalories(foodCalories);
                    Variables.setAdding(true);
                    finish();
                }else{
                    return;
                }
            }
        });
    }

    private boolean checkInputs(){
        String weightInput = itemWeight.getText().toString();
        if(weightInput.length() == 0){
            warning.setText(R.string.blankInput);
            return false;
        }else
            return true;
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
}
