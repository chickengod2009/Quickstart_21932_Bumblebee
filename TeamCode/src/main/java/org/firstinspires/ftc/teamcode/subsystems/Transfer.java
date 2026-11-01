package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Transfer {
    private CRServo transfer1;
    private CRServo transfer2;
    private double power;

    public Transfer(Master master) {
        transfer1 = master.get("transfer1");
        transfer2 = master.get("transfer2");
    }

    public void transferSetPower(double servoPower) {
        power = servoPower;
    }

    public double tiltGetPower() {
        return power;
    }

    public void update() {
        transfer1.setPower(power);
        transfer2.setPower(-power);
    }
}
