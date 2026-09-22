package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

public class Intake {
    private DcMotorEx intake;
    private DcMotorEx rollers;

    private double intakePower, rollerPower;
    private Util util;

    public Intake(Util util){
        this.util = util;
        intake = util.get("intakeMotor");
        rollers = util.get("rollersMotor");

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setIntakePower(double power) {
        intakePower = power;
    }

    public void setRollerPower(double power) {
        rollerPower = power;
    }

    public void setAllPower(double power) {
        intakePower = power;
        rollerPower = power;
    }

    public void update() {
        intake.setPower(intakePower);
        rollers.setPower(rollerPower);
    }
}