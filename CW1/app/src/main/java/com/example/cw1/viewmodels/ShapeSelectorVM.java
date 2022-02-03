package com.example.cw1.viewmodels;

import androidx.databinding.Bindable;

import com.example.cw1.BR;

public class ShapeSelectorVM extends ObservableVM {

    //Makes variables bindable
    //Allows us to monitor variable changes
    @Bindable
    private String shapeName;
    @Bindable
    private int brushWidth;

    //Create constructor to pass default values
    public ShapeSelectorVM() {
        shapeName = "Round";
        brushWidth = 20;
    }

    //Getter for the brush shape
    public String getShapeName() {
        return shapeName;
    }

    //Setter for the brush shape
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.shapeName);
    }

    //Getter for the brush width
    public int getBrushWidth() {
        return brushWidth;
    }

    //Setter for the brush width
    public void setBrushWidth(int brushWidth) {

        //Make sure input is greater than 0
        this.brushWidth = Math.max(brushWidth, 0);
        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.brushWidth);
    }
}