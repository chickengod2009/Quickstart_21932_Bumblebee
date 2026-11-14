package org.firstinspires.ftc.teamcode.OPS.test;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;

@TeleOp (name = "Intake Test")
public class TestIntake extends OpMode {
    public static DcMotorEx intake;
    public static DcMotorEx rollers;


    static double  targetPow;
    static double targetPow2;


    static double[] stepSizes = {.05,.07,.1,.15,.3,.5,.6,.8,.9,1};

    static int stepIndex = 1;





    public void init() {
        intake = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        rollers = hardwareMap.get(DcMotorEx.class, "rollersMotor");

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rollers.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addLine("Init complete");
        targetPow = 0;
        targetPow2 = 0;
    }



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
        telemetry.addData("Intake Velocity", intake.getVelocity());
        telemetry.addData("-------------------", 0);
        telemetry.addData("Rollers Target Power", targetPow2);
        telemetry.addData("Rollers Current Power", currentRollersPow);
        telemetry.addData("Rollers Velocity", rollers.getVelocity());
        telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
    }



}


