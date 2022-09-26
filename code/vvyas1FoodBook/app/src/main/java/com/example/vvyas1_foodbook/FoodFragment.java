package com.example.vvyas1_foodbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class FoodFragment extends DialogFragment {

    private EditText foodNameText;
    private EditText foodCostText;
    private EditText foodDescriptionText;
    private EditText foodLocationText;
    private EditText foodCountText;
    private DatePicker datepicker;

    private OnFragmentInteractionListener listener;
    int pos;
    int totalCost;
    int newTotalCost;
    String month1 = "";
    String day1 = "";
    String stringdate = "";



    public interface OnFragmentInteractionListener{

        public void onOkPressed(Food food, int pos, int cost);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            listener = (OnFragmentInteractionListener) context;
        }else
        {
            throw new RuntimeException(context.toString() + "must implement the interface methods");
        }
    }

    static FoodFragment newInstance(Food food, int pos){
        Bundle args = new Bundle();
        args.putInt("city", pos);
        args.putString("des", food.getDescription());
        args.putString("location", food.getLocation());
        args.putString("count", String.valueOf(food.getCount()));
        args.putString("cost", String.valueOf(food.getUnitCost()));
        args.putString("date", food.getBBD());
        args.putInt("tc",  food.getUnitCost()* food.getCount());
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_food, null);

        foodCostText = view.findViewById(R.id.editFoodCost);
        foodDescriptionText = view.findViewById(R.id.editFoodDescription);

        //https://developer.android.com/develop/ui/views/components/spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.editFoodLocation);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.location_menu, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        foodCountText = view.findViewById(R.id.editFoodCount);
        datepicker = view.findViewById(R.id.simpleDatePicker);


        if (getArguments() != null) {
            //initializing dialog box with existing object values
            pos = getArguments().getInt("city");
            foodDescriptionText.setText(getArguments().getString("des"));
            int spinnerPosition = adapter.getPosition(getArguments().getString("location"));
            totalCost = getArguments().getInt("tc");
            spinner.setSelection(spinnerPosition);
            foodCostText.setText(getArguments().getString("cost"));
            foodCountText.setText( getArguments().getString("count"));
            stringdate = getArguments().getString("date");

            //http://www.java2s.com/Code/Android/UI/SetinitialvalueforDatePicker.html
            //https://developer.android.com/reference/android/widget/DatePicker
            datepicker.updateDate(Integer.parseInt(stringdate.substring(0,4)), Integer.parseInt(stringdate.substring(5,7))-1, Integer.parseInt(stringdate.substring(8)));


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            return builder
                    .setView(view)
                    .setTitle("Update Food Item")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = "";

                            String cost = foodCostText.getText().toString();
                            String description = foodDescriptionText.getText().toString();
                            String location = spinner.getSelectedItem().toString();

                            String count = foodCountText.getText().toString();
                            String date;

                            newTotalCost = (int)Math.ceil(Float.parseFloat(cost))*Integer.parseInt(count);


                            int day = datepicker.getDayOfMonth();
                            int month = datepicker.getMonth();

                            //making sure to store two digits for day and month
                            if (day < 10)
                                day1 = "0" + String.valueOf(day);
                            else
                                day1 = String.valueOf(day);


                            //datepicker considers january to be 0
                            if (month < 10)
                                month1 = "0" + String.valueOf(month+1);
                            else
                                month1 = String.valueOf(month+1);

                            date = "" + datepicker.getYear()+ "-" + month1 + "-" + day1;


                            //wasn't sure if name and description were same so ended up having two attributes
                            Food newFood = new Food(description, Integer.parseInt(count), (int)Math.ceil(Float.parseFloat(cost)),date,location);

                            listener.onOkPressed(newFood, pos, newTotalCost - totalCost);
                        }
                    })

                    .create();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Food Item")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = "";
                        String cost = foodCostText.getText().toString();
                        String description = foodDescriptionText.getText().toString();
                        String location = spinner.getSelectedItem().toString();
                        String count = foodCountText.getText().toString();
                        String date;

                        int day = datepicker.getDayOfMonth();
                        int month = datepicker.getMonth();

                        //making sure to store two digits for day and month
                        if (day < 10)
                            day1 = "0" + String.valueOf(day);
                        else
                            day1 = String.valueOf(day);

                        if (month < 10)
                            month1 = "0" + String.valueOf(month+1);
                        else
                            month1 = String.valueOf(month+1);

                        date = "" + datepicker.getYear()+ "-" + month1 + "-" +day1;


                        //making a new food object
                        Food newFood = new Food(description, Integer.parseInt(count), (int)Math.ceil(Float.parseFloat(cost)),date,location);

                        listener.onOkPressed(newFood, -1, 0);
                    }
                })

                .create();


    }
}
