package com.example.cw1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw1.databinding.ActivityColourSelectorBinding;
import com.example.cw1.viewmodels.ColourSelectorVM;


public class ColourSelector extends AppCompatActivity {

    //Declare viewmodel object
    ColourSelectorVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Links the shape selector activity with it's viewmodel and creates binding object
        model = new ViewModelProvider(this).get(ColourSelectorVM.class);

        ActivityColourSelectorBinding colourSelectorBinding = ActivityColourSelectorBinding.inflate(LayoutInflater.from(this));
        setContentView(colourSelectorBinding.getRoot());
        colourSelectorBinding.setViewmodel(model);

        //If there are saved values in the bundle store them
        Bundle bundleReceive = getIntent().getExtras();

        if (bundleReceive != null) {
            model.setColourName(bundleReceive.getString("colourReturn"));
        }

        //Array of list options
        String[] colourOptions = new String[]{"Red",
                "Green",
                "Blue",
                "Purple",
                "Black",
        };

        //Allows the creation of views for each object in our ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, colourOptions);

        //Link ListView to adapter
        colourSelectorBinding.colourList.setAdapter(adapter);
        //Set listener to know when a list item is pressed
        //If pressed set the colour name to the option selected
        colourSelectorBinding.colourList.setOnItemClickListener((parent, view, position, id) ->
                model.setColourName((String) colourSelectorBinding.colourList.getItemAtPosition(position)));

        //Add button listener
        colourSelectorBinding.returnColourButton.setOnClickListener(v -> {
            //If pressed store the colour value in a bundle and finish the activity to go to paint canvas
            Bundle bundleTo = new Bundle();
            bundleTo.putString("colourTo", model.getColourName());
            Intent result = new Intent();
            result.putExtras(bundleTo);
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }

    //Save the instance state in case an instance is destroyed when rotating the app etc
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Store all variables necessary
        savedInstanceState.putString("selectedColourName", model.getColourName());
    }

    //On restore of an instance retrieve all data stored and set all options back
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Retrieve data
        model.setColourName(savedInstanceState.getString("selectedColourName"));
    }
}