package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.control.log.Loggable;
import org.firstinspires.ftc.teamcode.control.functionality.DefaultUpdate;

public class Intake implements Loggable, DefaultUpdate {
    private DcMotorEx intake;
    private DcMotorEx rollers;
    public static double intakeSpeed = .4;

    private double intakePower, rollerPower;
    private Master master;

    public Intake(Master master){
        this.master = master;
        intake = master.get("intake");
        rollers = master.get("roller");

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
    public double getIntakePower() {
        return intake.getPower();
    }
    public double getRollersPower() {
        return rollers.getPower();
    }

    @Override
    public void update() {
        intake.setPower(intakePower);
        rollers.setPower(rollerPower);
    }

    @Override
    public String log() {
        throw Master.TODO();
    }
}