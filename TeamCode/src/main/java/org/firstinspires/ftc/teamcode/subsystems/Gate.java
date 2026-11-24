package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Gate {
    private final Servo gate, gate2;

    public static double NECTAR_CLOSE = .23, NECTAR_OPEN = 0, POLLEN_CLOSE = 0.21, POLLEN_OPEN = 0;

    private double position = POLLEN_CLOSE, position2 = NECTAR_CLOSE;

    public Gate(Master master) {
        gate = master.get("gate");
        gate2 = master.get("gate2");
        gate.setDirection(Servo.Direction.FORWARD);
        gate2.setDirection(Servo.Direction.REVERSE);
    }

    public void setPosition(double gatePosition){
        position = gatePosition;
    }

    public void setAllPosition(String pos) {
        if(pos.equalsIgnoreCase("open")) {
            position = POLLEN_OPEN;
            position2 = NECTAR_OPEN;
        }
        else if(pos.equalsIgnoreCase("close")) {
            position = POLLEN_CLOSE;
            position2 = NECTAR_CLOSE;
        }
    }



    public void update() {
        gate.setPosition(position);
        gate2.setPosition(position2);
    }

    public double getPosition() {
        return gate.getPosition();
    }
}