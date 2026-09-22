package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class MiddleSensor {
    ColorSensor middleSensor;

    public enum Color {
        GREEN, UNKNOWN, PURPLE

    }
    private Color color;
    private float R, G, B;
    public int ct, noise;
    public MiddleSensor(@NotNull Util util) {
        middleSensor = util.get("middleSensor");
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
            color = Color.UNKNOWN;
        } else if (G > 110 && G > B) {
            color = Color.GREEN;
        } else {
            color = Color.PURPLE;
        }

    }
    public Color getColor() {
        return color;
    }
    public float getR() {return R;}
    public float getG() {return G;}
    public float getB() {return B;}

    public int hasBall() {
        return (!(Objects.equals(color, Color.UNKNOWN))) ? 1 : 0;
    }

}