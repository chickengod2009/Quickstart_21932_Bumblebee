package org.firstinspires.ftc.teamcode.OPS.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.subsystems.Util;

@TeleOp(name = "Intake Test")
public class IntakeTest extends OpMode{
    public DcMotorEx intake;
    public DcMotorEx rollers;


    double targetPow;
    double targetPow2;


    double[] stepSizes = {.1, .5, .75, 1};

    int stepIndex = 1;

    Util util;


    @Override
    public void init() {
        intake = util.get("intakeMotor");
        rollers = util.get("rollersMotor");

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rollers.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addLine("Init complete");
        targetPow = 0;
        targetPow2 = 0;
    }


    @Override
    public void loop() {
        //get all our gamepad commands
        //set target velocity
        //update telemetry


        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadUpWasPressed()) {
            targetPow += stepSizes[stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()) {
            targetPow -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()) {
            targetPow2 += stepSizes[stepIndex];
        }
        if (gamepad1.dpadLeftWasPressed()) {
            targetPow2 -= stepSizes[stepIndex];
        }


        // set velocity
        intake.setPower(targetPow);
        rollers.setPower(targetPow2);

        double currentIntakePow = intake.getPower();
        double currentRollersPow = rollers.getPower();


        telemetry.addData("Intake Target Power", targetPow);
        telemetry.addData("Intake Current Power", currentIntakePow);
        telemetry.addData("-------------------", 0);
        telemetry.addData("Rollers Target Power", targetPow2);
        telemetry.addData("Rollers Current Power", currentRollersPow);
        telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
    }
}


