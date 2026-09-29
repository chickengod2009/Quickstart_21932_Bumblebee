package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Gate {
    private Servo gate;

    public static double CLOSE = 0.4, OPEN = 0.1;
    private double position = OPEN;

    public Gate(Util util) {
        gate = util.get("gate");
    }

    public void setPosition(double gatePosition){
        position = gatePosition;
    }



    public void update() {
        gate.setPosition(position);
    }

    public double getPosition() {
        return gate.getPosition();
    }
}