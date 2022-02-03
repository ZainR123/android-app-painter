package com.example.cw1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw1.databinding.ActivityMainBinding;
import com.example.cw1.viewmodels.FingerPainterVM;

public class FingerPainter extends AppCompatActivity {

    //Declare viewmodel and data binding object
    FingerPainterVM model;
    ActivityMainBinding activityMainBinding;

    //Creates launcher which receives data from the ColourSelector activity
    //Sets the colour for the paint canvas
    ActivityResultLauncher<Intent> launcherColour = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    Bundle bundleReceive = data.getExtras();
                    model.setColourName(bundleReceive.getString("colourTo"));
                }
            });

    //Creates launcher which receives data from the ShapeSelector activity
    //Sets the shape and size of the brush for the paint canvas
    ActivityResultLauncher<Intent> launcherShapeSize = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    Bundle bundleReceive = data.getExtras();
                    model.setShapeName(bundleReceive.getString("shapeTo"));
                    model.setWidthCode(bundleReceive.getInt("widthTo"));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Links the main activity with it's viewmodel and creates binding object
        model = new ViewModelProvider(this).get(FingerPainterVM.class);

        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setContentView(activityMainBinding.getRoot());
        activityMainBinding.setViewmodel(model);

        //If an image is trying to be opened in the app then load the intent data to the paint canvas
        if (getIntent().getData() != null) {
            activityMainBinding.fingerPainterView.load(getIntent().getData());
        }

        //Array of list options
        String[] paintOptions = new String[]{"Colour Selection", "Shape and Size Selection"};

        //Allows the creation of views for each object in our ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, paintOptions);

        //Link ListView to adapter
        activityMainBinding.selector.setAdapter(adapter);
        //Set listener to know when a list item is pressed
        activityMainBinding.selector.setOnItemClickListener((parent, view, position, id) -> {

            //Save list position pressed to string
            String selectedOption = (String) activityMainBinding.selector.getItemAtPosition(position);
            Bundle bundleTo = new Bundle();
            Intent intent;
            //If the "Colour Selection" item was pressed then form a new intent and send data
            //to the "ColourSelector" activity in a bundle
            if (selectedOption.equals("Colour Selection")) {
                intent = new Intent(this, ColourSelector.class);
                bundleTo.putString("colourReturn", model.getColourName());
                intent.putExtras(bundleTo);
                launcherColour.launch(intent);
            }
            //If the "Shape and Size Selection" item was pressed then form a new intent and send data
            //to the "ShapeSelector" activity in a bundle
            else if (selectedOption.equals("Shape and Size Selection")) {
                intent = new Intent(this, ShapeSelector.class);
                bundleTo.putString("shapeReturn", model.getShapeName());
                bundleTo.putInt("widthReturn", model.getWidthCode());
                intent.putExtras(bundleTo);
                launcherShapeSize.launch(intent);
            }
        });
    }

    //Save the instance state in case an instance is destroyed when rotating the app etc
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Store all variables necessary
        savedInstanceState.putString("selectedShape", model.getShapeName());
        savedInstanceState.putInt("selectedWidth", model.getWidthCode());
        savedInstanceState.putString("selectedColourName", model.getColourName());
    }

    //On restore of an instance retrieve all data stored and set all options back
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Retrieve data
        model.setShapeName(savedInstanceState.getString("selectedShape"));
        model.setWidthCode(savedInstanceState.getInt("selectedWidth"));
        model.setColourName(savedInstanceState.getString("selectedColourName"));
    }
}