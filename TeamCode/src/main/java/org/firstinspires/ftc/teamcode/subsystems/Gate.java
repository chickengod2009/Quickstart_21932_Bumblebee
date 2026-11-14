package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Gate {
    private Servo gate, gate2;

    public static double CLOSE = 0.4, OPEN = 0.1;
    private double position = OPEN;

    public Gate(Master master) {
        gate = master.get("gate");
        gate2 = master.get("gate2");
    }

    public void setPosition(double gatePosition){
        position = gatePosition;
    }



    public void update() {
        gate.setPosition(position);
        gate2.setPosition(position);
    }

    public double getPosition() {
        return gate.getPosition();
    }
}