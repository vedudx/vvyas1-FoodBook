package com.example.vvyas1_foodbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


//I have added links to all the resources wherever I used them
//I have reused code provided to us for lab3
public class MainActivity extends AppCompatActivity implements FoodFragment.OnFragmentInteractionListener {


    // Declare the variables so that you will be able to reference it later.
    ListView foodList;
    TextView totalCostView;
    ArrayAdapter<Food> foodAdapter;
    ArrayList<Food> dataList;
    FloatingActionButton addButton;
    FloatingActionButton deleteButton;
    FloatingActionButton editButton;
    int totalCost = 0;
    int remove_pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodList = findViewById(R.id.FoodListViewId);

        totalCostView = findViewById(R.id.totalCost);

        dataList = new ArrayList<>();

        foodAdapter = new FoodAdapter(this, dataList);

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item= String.valueOf(adapterView.getItemAtPosition(i));

                if (remove_pos != -1){
                    //adapterView.getChildAt(remove_pos).setBackgroundColor(Color.WHITE);
                }
                //view.setBackgroundColor(Color.LTGRAY);
                remove_pos = i;         //To know which list item was clicked
            }
        });

        foodList.setAdapter(foodAdapter);

        addButton = findViewById(R.id.floatingAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodFragment ff = new FoodFragment();
                //calling show method to show the fragment dialog box
                ff.show(getSupportFragmentManager(), "ADD_FOOD");
            }
        });

        deleteButton = findViewById(R.id.floatingDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remove_pos != -1)
                {
                    if (remove_pos < dataList.size())
                    {
                        //updating totalcost
                        totalCost -= dataList.get(remove_pos).getUnitCost()*dataList.get(remove_pos).getCount();
                        dataList.remove(remove_pos);
                        totalCostView.setText("$"+String.valueOf(totalCost));
                        foodAdapter.notifyDataSetChanged();
                    }
                    remove_pos = -1;            //changing back remove_pos to -1 since our list item is not active anymore
                }
            }
        });


        editButton = findViewById(R.id.floatingEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (remove_pos != -1)
                {
                    if (remove_pos < dataList.size())       //checking the bounds
                    {
                        //calling new instance method since we also want to pass food object
                        FoodFragment __myFragment = FoodFragment.newInstance(dataList.get(remove_pos), remove_pos);

                        __myFragment.show(getSupportFragmentManager(), "EDIT_FOOD");
                    }

                }



            }
        });


    }




    @Override
    public void onOkPressed(Food food, int pos, int newCost) {
        //since the items can be updated , i pass in the change of cost whenever item is updated
        if (pos == -1)
        {
            dataList.add(food);
            totalCost += food.getUnitCost()*food.getCount();
            totalCostView.setText("$"+String.valueOf(totalCost));
            foodAdapter.notifyDataSetChanged();
        }
        else
        {
            dataList.get(pos).setCount(food.getCount());
            dataList.get(pos).setDescription(food.getDescription());
            dataList.get(pos).setLocation(food.getLocation());
            dataList.get(pos).setUnitCost(food.getUnitCost());
            dataList.get(pos).setBBD(food.getBBD());
            totalCost += newCost;
            totalCostView.setText("$"+String.valueOf(totalCost));
            foodAdapter.notifyDataSetChanged();
        }

    }
}

