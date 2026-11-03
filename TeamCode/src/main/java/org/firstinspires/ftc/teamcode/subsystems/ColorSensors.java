package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.Objects;

public class ColorSensors {
    ColorSensor firstSensor, secondSensor, thirdSensor;
    private String color1, color2, color3;
    private float R1, G1, B1;
    private float R2, G2, B2;
    private float R3, G3, B3;
    public int ct, noise;
    public ColorSensors(Master master) {
        firstSensor = master.get("topSensor");
        secondSensor = master.get("middleSensor");
        thirdSensor = master.get("bottomSensor");

        noise = 200;
        //topSensor.setGain(4);
    }

    public void update() {
        R1 = firstSensor.red();
        G1 = firstSensor.green();
        B1 = firstSensor.blue();

        R2 = secondSensor.red();
        G2 = secondSensor.green();
        B2 = secondSensor.blue();

        R3 = thirdSensor.red();
        G3 = thirdSensor.green();
        B3 = thirdSensor.blue();

        //TODO: find parameters for colors
        //FIRST
        if ((R1 + G1 + B1) < noise) {
            color1 = "UNKNOWN";
        } else if (G1 > 110 && G1 > B1) {
            color1 = "GREEN";
        } else {
            color1 = "PURPLE";
        }
        //SECOND
        if ((R1 + G1 + B1) < noise) {
            color2 = "UNKNOWN";
        } else if (G1 > 110 && G1 > B1) {
            color2 = "GREEN";
        } else {
            color2 = "PURPLE";
        }
        //THIRD
        if ((R1 + G1 + B1) < noise) {
            color3 = "UNKNOWN";
        } else if (G1 > 110 && G1 > B1) {
            color3 = "GREEN";
        } else {
            color3 = "PURPLE";
        }

    }
    public String getColor(ColorSensor sensor) {

        if (sensor == firstSensor) {
            return color1;
        } else if (sensor == secondSensor) {
            return color2;
        } else if (sensor == thirdSensor) {
            return color3;
        }
        return color1;
    }
    public float getR(ColorSensor sensor) {
        if (sensor == firstSensor) {
            return R1;
        } else if (sensor == secondSensor) {
            return R2;
        } else if (sensor == thirdSensor) {
            return R3;
        }
        return R1;
    }
    public float getG(ColorSensor sensor) {
        if (sensor == firstSensor) {
            return G1;
        } else if (sensor == secondSensor) {
            return G2;
        } else if (sensor == thirdSensor) {
            return G3;
        }
        return G1;
    }
    public float getB(ColorSensor sensor) {
        if (sensor == firstSensor) {
            return B1;
        } else if (sensor == secondSensor) {
            return B2;
        } else if (sensor == thirdSensor) {
            return B3;
        }
        return B1;
    }

    public int hasBall(ColorSensor sensor) {


        if (sensor == firstSensor) {
            return (!(Objects.equals(color1, "UNKNOWN"))) ? 1 : 0;
        } else if (sensor == secondSensor) {
            return (!(Objects.equals(color2, "UNKNOWN"))) ? 1 : 0;
        } else if (sensor == thirdSensor) {
            return (!(Objects.equals(color3, "UNKNOWN"))) ? 1 : 0;
        }
        return (!(Objects.equals(color1, "UNKNOWN"))) ? 1 : 0;
    }

}