package com.example.cw1.viewmodels;

import androidx.databinding.Bindable;

import com.example.cw1.BR;

public class ColourSelectorVM extends ObservableVM {

    //Makes variables bindable
    //Allows us to monitor variable changes
    @Bindable
    private String colourName;

    //Create constructor to pass default values
    public ColourSelectorVM() {
        colourName = "Black";
    }

    //Getter for the colour
    public String getColourName() {
        return colourName;
    }

    //Setter for the colour
    public void setColourName(String colourName) {
        this.colourName = colourName;
        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.colourName);
    }
}
