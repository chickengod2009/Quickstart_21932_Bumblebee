package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.Servo;

public class FlowerRamp {
    private Servo ramp;
    private double position;
    public static double DOWN = 0.42, UP = 0;


    public FlowerRamp(Master master) {
        ramp = master.get("ramp");
    }

    public void setRampPosition(Double pos) {
        position = pos;
    }
    public double getRampPosition() {
        return position;
    }

    public void update() {
        ramp.setPosition(position);
    }
}
