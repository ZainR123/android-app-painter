package com.example.cw1.viewmodels;

import android.graphics.Paint;

import androidx.databinding.Bindable;

import com.example.cw1.BR;

public class FingerPainterVM extends ObservableVM {

    //Makes variables bindable
    //Allows us to monitor variable changes
    @Bindable
    private String colourName;
    @Bindable
    private String shapeName;
    @Bindable
    private int widthCode;
    @Bindable
    private int colourCode;
    @Bindable
    private Paint.Cap shapeCode;

    //Create constructor to pass default values
    public FingerPainterVM() {
        colourName = "Black";
        shapeName = "Round";
        widthCode = 20;
        colourCode = 0xFF000000;
        shapeCode = Paint.Cap.ROUND;
    }

    //Getter for the colour code
    public int getColourCode() {
        return colourCode;
    }

    //Setter for the colour code
    public void setColourCode() {

        //Set colour code value depending on selected colour
        switch (getColourName()) {
            case "Green":
                colourCode = 0xFF00FF00;
                break;
            case "Red":
                colourCode = 0xFFFF0000;
                break;
            case "Blue":
                colourCode = 0xFF0000FF;
                break;
            case "Purple":
                colourCode = 0xFF800080;
                break;
            case "Black":
                colourCode = 0xFF000000;
                break;
        }
        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.colourCode);
    }

    //Getter for the shape code
    public Paint.Cap getShapeCode() {
        return shapeCode;
    }

    //Setter for the shape code
    public void setShapeCode() {

        //Set shape code value depending on selected brush type
        if (getShapeName().equals("Round")) {
            shapeCode = Paint.Cap.ROUND;
        } else if (getShapeName().equals("Square")) {
            shapeCode = Paint.Cap.SQUARE;
        }

        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.shapeCode);
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
        setColourCode();
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
        setShapeCode();
    }

    //Getter for the brush width
    public int getWidthCode() {
        return widthCode;
    }

    //Setter for the brush width
    public void setWidthCode(int widthCode) {
        this.widthCode = widthCode;
        //Update observer class that the variable has been updated
        notifyPropertyChanged(BR.widthCode);
    }
}
