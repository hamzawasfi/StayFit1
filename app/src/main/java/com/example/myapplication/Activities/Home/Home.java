package com.example.myapplication.Activities.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBase.users.DataModels.DayModel;
import com.example.myapplication.DataBase.users.DaysDataBase;
import com.example.myapplication.DataBase.users.FoodDataBase;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Variables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity {

    //lists
    private ArrayList<String> listItems;
    private ArrayList<String> addedItems;
    private ArrayAdapter listAdapter;
    //

    //objects
    private FoodDataBase foodDB;
    private DaysDataBase dayDB;
    //

    //strings
    private String username;
    private String goalCalories;
    private String currentDate;
    //

    private SimpleDateFormat date;

    //doubles
    private double totalCalories;
    private double caloriePrecentage;
    //

    //design objects
    private ImageView searchbtn, deletebtn, editbtn;
    private EditText searchContent;
    private Button nextDay, previousDay, addFood;
    private ProgressBar calorieProgress;
    private TextView calorieCount;
    private ListView itemsList, addedList;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //variable intializations
        dayDB = new DaysDataBase(this);
        foodDB = new FoodDataBase(this);
        foodDB.addFoodModel();
        dayDB.addDayModel();
        Toast.makeText(getApplicationContext(), Variables.getDays().size() + " ", Toast.LENGTH_SHORT).show();

        username = Variables.getUsername();
        goalCalories = String.valueOf(Variables.getUsers().get(getUserIndex(username)).getGoalCalories());

        listItems = new ArrayList<String>();
        addedItems = new ArrayList<String>();

        date = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = date.format(new Date());
        Variables.setDate(currentDate);
        //

        //UI casting
        deletebtn = (ImageView)findViewById(R.id.delete_item);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAddedItem();
                addedItems = new ArrayList<String>();
                displayAddedItems(addedItems, getDayIndexes(username, currentDate));
                deletebtn.setVisibility(View.INVISIBLE);
                editbtn.setVisibility(View.INVISIBLE);
                Variables.setEditing(true);
                onPostResume();
            }
        });

        addedList = (ListView)findViewById(R.id.added_list);
        addedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Variables.setIndex(position);
                Variables.setIndex1(getRelatedItemIndex());
                deletebtn.setVisibility(View.VISIBLE);
                editbtn.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchbtn = (ImageView) findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editbtn = (ImageView)findViewById(R.id.edit_item);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodInfo();
                editbtn.setVisibility(View.INVISIBLE);
                deletebtn.setVisibility(View.INVISIBLE);
            }
        });

        searchContent = (EditText)findViewById(R.id.search_content);

        addFood = (Button)findViewById(R.id.add_foodbtn);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsertItem();
            }
        });

        nextDay = (Button)findViewById(R.id.next_daybtn);
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextDay();
            }
        });

        previousDay = (Button)findViewById(R.id.previous_daybtn);
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousDay();
            }
        });

        calorieCount = (TextView)findViewById(R.id.estimated_caloriestxt);
        calorieCount.setText(goalCalories);

        calorieProgress = (ProgressBar)findViewById(R.id.calorie_progress_bar);

        itemsList = (ListView)findViewById(R.id.food_list);
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Variables.setIndex(position);
                openAddItem();
            }
        });

        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Home.this).listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //

        //functions onStart
        displayItems(listItems, getFoodIndexes(username));
        displayAddedItems(addedItems, getDayIndexes(username, currentDate));
        //
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (Variables.isAdding() || Variables.isDateChanged() || Variables.isEditing()) {
            addedItems = new ArrayList<String>();
            displayAddedItems(addedItems, getDayIndexes(username, currentDate));
            Variables.setAdding(false);
            Variables.setDateChanged(false);
            Variables.setEditing(false);
        }else if(Variables.isInserting()){
            listItems = new ArrayList<String>();
            displayItems(listItems, getFoodIndexes(username));
            Variables.setInserting(false);
        }

        totalCalories = DisplayTotalCalories(getDayIndexes(username, currentDate));
        caloriePrecentage = progress(totalCalories);
        calorieProgress.setProgress((int)caloriePrecentage);
    }

    public int getUserIndex(String username){
        for(int i = 0; i < Variables.getUsers().size(); i++) {
            if (Variables.getUsers().get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return 0;
    }

    public ArrayList<Integer> getFoodIndexes(String username){
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for(int i = 0; i < Variables.getFoods().size(); i++) {
            if (Variables.getFoods().get(i).getUsername().equals(username) || Variables.getFoods().get(i).getUsername().equals("global")) {
                indexes.add(i);
            }
        }
        Variables.setFoodIndexes(indexes);
        return indexes;
    }

    public ArrayList<Integer> getDayIndexes(String username, String date){
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for(int i = 0; i < Variables.getDays().size(); i++){
            if(Variables.getDays().get(i).getUsername().equals(username) && Variables.getDays().get(i).getDate().equals(date)){
                indexes.add(i);
            }
        }
        Variables.setDayIndexes(indexes);
        return indexes;
    }

    private int getRelatedItemIndex() {
        for(int i = 0; i < Variables.getFoods().size(); i++) {
            if (Variables.getFoods().get(i).getID() == Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getFoodID()) {
                Variables.setIndex1(i);
                return i;
            }
        }
        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
        return 0;
    }
    //onCreate

    public void displayItems(ArrayList<String> list, ArrayList<Integer> indexes){
        for(int i = 0; i < indexes.size(); i++) {
            list.add(Variables.getFoods().get(indexes.get(i)).getFoodName() + "  " + Variables.getFoods().get(indexes.get(i)).getFoodCal() + "cal/100gm.");
        }
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        itemsList.setAdapter(listAdapter);
        Variables.setListItems(list);
    }

    public void displayAddedItems(ArrayList<String> list, ArrayList<Integer> indexes){
        for(int i = 0; i < indexes.size(); i++){
            list.add(Variables.getDays().get(indexes.get(i)).getFoodName() + "  " + Variables.getDays().get(indexes.get(i)).getFoodCal() + "cal.");
        }
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        addedList.setAdapter(listAdapter);
        Variables.setAddedItems(list);
    }

    public int DisplayTotalCalories(ArrayList<Integer> indexes){
        int total = 0;
        for(int i = 0; i < indexes.size(); i++){
            total += Variables.getDays().get(indexes.get(i)).getFoodCal();
        }
        return total;
    }

   // edit and remove

    public void removeAddedItem(){
        List<DayModel> newModel;
        dayDB.deleteRow(String.valueOf(Variables.getDays().get(Variables.getDayIndexes().get(Variables.getIndex())).getID()));
        int x = Variables.getDayIndexes().get(Variables.getIndex());
        newModel = Variables.getDays();
        newModel.remove(x);
        Variables.setDays(newModel);
    }

    //

    public double progress(double currentCalories){
        double userCalories = Integer.parseInt(goalCalories);
        double progress = (currentCalories / userCalories) * 100;
        if(progress > 100){
            progress = 100;
        }
        return progress;
    }

    //intents

    public void openFoodInfo(){
        Intent foodInfoIntent = new Intent(this, FoodInfo.class);
        startActivity(foodInfoIntent);
    }

    public void openInsertItem(){
        Intent insertItemIntent = new Intent(this, InsertItem.class);
        startActivity(insertItemIntent);
    }

    public void openAddItem(){
        Intent addItemIntent = new Intent(this, AddItem.class);
        startActivity(addItemIntent);
    }

    public void goToNextDay(){

        Variables.setDateChanged(true);
        Variables.setAddedItems(new ArrayList<String>());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        currentDate = sdf.format(c.getTime());
        Toast.makeText(getApplicationContext(), currentDate, Toast.LENGTH_SHORT).show();

        Variables.setDate(currentDate);
        onPostResume();
    }

    public void goToPreviousDay(){

        Variables.setDateChanged(true);
        Variables.setAddedItems(new ArrayList<String>());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -1);  // number of days to add
        currentDate = sdf.format(c.getTime());  // dt is now the new date
        Toast.makeText(getApplicationContext(), currentDate, Toast.LENGTH_SHORT).show();

        Variables.setDate(currentDate);
        onPostResume();
    }

    //
}
