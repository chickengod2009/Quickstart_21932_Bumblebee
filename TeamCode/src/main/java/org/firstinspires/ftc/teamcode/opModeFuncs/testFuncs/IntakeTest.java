package org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;


public class IntakeTest{
    public DcMotorEx intake;
    public DcMotorEx rollers;


    double targetPow;
    double targetPow2;


    double[] stepSizes = {.1, .5, .75, 1};

    int stepIndex = 1;





    public void init(MasterTrait master) {
        intake = master.get("intakeMotor");
        rollers = master.get("rollersMotor");

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rollers.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        master.getOpMode().telemetry.addLine("Init complete");
        targetPow = 0;
        targetPow2 = 0;
    }


    
    public void loop(MasterTrait master) {
        //get all our gamepad commands
        //set target velocity
        //update master.getOpMode().telemetry


        if (master.getOpMode().gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (master.getOpMode().gamepad1.dpadUpWasPressed()) {
            targetPow += stepSizes[stepIndex];
        }
        if (master.getOpMode().gamepad1.dpadDownWasPressed()) {
            targetPow -= stepSizes[stepIndex];
        }
        if (master.getOpMode().gamepad1.dpadRightWasPressed()) {
            targetPow2 += stepSizes[stepIndex];
        }
        if (master.getOpMode().gamepad1.dpadLeftWasPressed()) {
            targetPow2 -= stepSizes[stepIndex];
        }


        // set velocity
        intake.setPower(targetPow);
        rollers.setPower(targetPow2);

        double currentIntakePow = intake.getPower();
        double currentRollersPow = rollers.getPower();


        master.getOpMode().telemetry.addData("Intake Target Power", targetPow);
        master.getOpMode().telemetry.addData("Intake Current Power", currentIntakePow);
        master.getOpMode().telemetry.addData("-------------------", 0);
        master.getOpMode().telemetry.addData("Rollers Target Power", targetPow2);
        master.getOpMode().telemetry.addData("Rollers Current Power", currentRollersPow);
        master.getOpMode().telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
    }
}


