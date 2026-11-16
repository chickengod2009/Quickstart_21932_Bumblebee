package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FlowerRamp {
    private Servo ramp;
    private double position;
    public static double DOWN = 0.4, UP = 0.1;


    public FlowerRamp(Master master) {
        ramp = master.get("ramp1");
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
