package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.HashMap;
import java.util.Objects;

public class MiddleSensor {
    ColorSensor middleSensor;
    private String color;
    private float R, G, B;
    public int ct, noise;
    public MiddleSensor(HardwareMap hwMap, HashMap<String, String> config) {
        middleSensor = hwMap.get(ColorSensor.class, config.get("middleSensor"));
        noise = 160;
        //topSensor.setGain(4);
    }

    public void update() {
        R = middleSensor.red();
        G = middleSensor.green();
        B = middleSensor.blue();
/*
        telemetry.addLine("TOP SENSOR");
        telemetry.addData("red", normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);
        telemetry.addData("Color: ", getColor());
*/
        if ((R + G + B) < noise) {
            color = "UNKNOWN";
        } else if (G > 110 && G > B) {
            color = "GREEN";
        } else {
            color = "PURPLE";
        }

    }
    public String getColor() {
        return color;
    }
    public float getR() {return R;}
    public float getG() {return G;}
    public float getB() {return B;}

    public int hasBall() {
        return (!(Objects.equals(color, "UNKNOWN"))) ? 1 : 0;
    }

}