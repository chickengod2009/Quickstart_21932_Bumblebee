package org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;


public class IntakeTest{
    public static DcMotorEx intake;
    public static DcMotorEx rollers;


    static double  targetPow;
    static double targetPow2;


    static double[] stepSizes = {.05,.07,.1,.15,.3,.5,.6,.8,.9,1};

    static int stepIndex = 1;





    public static void init(MasterTrait master) {
        intake = master.getOpMode().hardwareMap.get(DcMotorEx.class, "intakeMotor");
        rollers = master.getOpMode().hardwareMap.get(DcMotorEx.class, "rollersMotor");

        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rollers.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rollers.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rollers.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        master.getOpMode().telemetry.addLine("Init complete");
        targetPow = 0;
        targetPow2 = 0;
    }


    
    public static void loop(MasterTrait master) {
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


