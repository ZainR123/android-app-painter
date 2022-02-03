package com.example.cw1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw1.databinding.ActivityShapeSelectorBinding;
import com.example.cw1.viewmodels.ShapeSelectorVM;

public class ShapeSelector extends AppCompatActivity {

    //Declare viewmodel object
    ShapeSelectorVM model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Links the shape selector activity with it's viewmodel and creates binding object
        model = new ViewModelProvider(this).get(ShapeSelectorVM.class);

        ActivityShapeSelectorBinding shapeSelectorBinding = ActivityShapeSelectorBinding.inflate(LayoutInflater.from(this));
        setContentView(shapeSelectorBinding.getRoot());
        shapeSelectorBinding.setViewmodel(model);

        //If there are saved values in the bundle store them
        Bundle bundleReceive = getIntent().getExtras();


        if (bundleReceive != null) {
            model.setShapeName(bundleReceive.getString("shapeReturn"));
            model.setBrushWidth(bundleReceive.getInt("widthReturn"));
        }

        //Array of list options
        String[] brushOptions = new String[]{"Square", "Round"};

        //Allows the creation of views for each object in our ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, brushOptions);

        //Link ListView to adapter
        shapeSelectorBinding.brushList.setAdapter(adapter);
        //Set listener to know when a list item is pressed
        //If pressed set the shape name to the option selected
        shapeSelectorBinding.brushList.setOnItemClickListener((parent, view, position, id) ->
                model.setShapeName((String) shapeSelectorBinding.brushList.getItemAtPosition(position)));

        //Add listener for number input
        shapeSelectorBinding.widthBrush.addTextChangedListener(new TextWatcher() {

            //Then store inputted brush width
            public void afterTextChanged(Editable s) {

                //Make sure input is not empty then pass inputted brush width to be saved
                if (!shapeSelectorBinding.widthBrush.getText().toString().equals("")) {
                    model.setBrushWidth(Integer.parseInt(shapeSelectorBinding.widthBrush.getText().toString()));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Add button listener
        shapeSelectorBinding.returnShapeSizeButton.setOnClickListener(v -> {

            //If pressed store brush values in a bundle and finish the activity to go to paint canvas
            Bundle bundleTo = new Bundle();
            bundleTo.putString("shapeTo", model.getShapeName());
            bundleTo.putInt("widthTo", model.getBrushWidth());
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
        savedInstanceState.putString("selectedShape", model.getShapeName());
        savedInstanceState.putInt("selectedWidth", model.getBrushWidth());
    }

    //On restore of an instance retrieve all data stored and set all options back
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Retrieve data
        model.setShapeName(savedInstanceState.getString("selectedShape"));
        model.setBrushWidth(savedInstanceState.getInt("selectedWidth"));
    }
}