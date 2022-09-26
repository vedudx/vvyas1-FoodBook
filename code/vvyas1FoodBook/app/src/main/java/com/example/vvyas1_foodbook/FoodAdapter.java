package com.example.vvyas1_foodbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food>{
    private Context context;
    private ArrayList<Food> dataList;
    public FoodAdapter(Context context, ArrayList<Food> dataList)
    {
        super(context,0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent,false);
        }
        TextView foodNameText = view.findViewById(R.id.foodName);
        TextView foodCostText = view.findViewById(R.id.foodCost);
        TextView foodCountText = view.findViewById(R.id.foodCount);
        TextView foodLocationText = view.findViewById(R.id.location);
        TextView bestBeforeText = view.findViewById(R.id.Date);


        Food currentFoodItem = dataList.get(position);

        foodNameText.setText(currentFoodItem.getDescription());

        foodCostText.setText("$"+String.valueOf(currentFoodItem.getUnitCost()));

        foodCountText.setText(String.valueOf(currentFoodItem.getCount()));
        foodLocationText.setText(currentFoodItem.getLocation());

        bestBeforeText.setText(currentFoodItem.getBBD());

        return view;
    }

}


