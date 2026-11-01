package org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;

public class FlywheelTest{
    public static DcMotorEx Flywheel1;
    public static DcMotorEx Flywheel2;

    public static double lowVel = 1500;

    public static double highVel = 2100;

    static double targetVel;
    static double targetVel2;
    static boolean pidfMode;

    static double F = 0;
    static double P = 0;

    static double[] stepSizes = {50.0, 10.0, 1.0, 0.5, 0.05, .005, .0005};

    static int stepIndex = 1;



    
    public static void init(MasterTrait master) {
        Flywheel1 = master.getOpMode().hardwareMap.get(DcMotorEx.class, "flyMotor");
        Flywheel2 = master.getOpMode().hardwareMap.get(DcMotorEx.class, "flyMotor2");
        Flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel2.setDirection(DcMotorEx.Direction.REVERSE);
        Flywheel1.setDirection(DcMotorEx.Direction.REVERSE);


        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        Flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        Flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        master.getOpMode().telemetry.addLine("Init complete");
        pidfMode = false;
        targetVel = 0;
    }


    
    public static void loop(MasterTrait master) {
        //get all our gamepad commands
        //set target velocity
        //update master.getOpMode().telemetry

        if (master.getOpMode().gamepad1.aWasPressed()) {
            pidfMode = !pidfMode;
        }
        if (!pidfMode) {
            if (master.getOpMode().gamepad1.bWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }
            if (master.getOpMode().gamepad1.dpadUpWasPressed()) {
                targetVel += stepSizes[stepIndex];
            }
            if (master.getOpMode().gamepad1.dpadDownWasPressed()) {
                targetVel -= stepSizes[stepIndex];
            }
            if (master.getOpMode().gamepad1.dpadRightWasPressed()) {
                targetVel2 += stepSizes[stepIndex];
            }
            if (master.getOpMode().gamepad1.dpadLeftWasPressed()) {
                targetVel2 -= stepSizes[stepIndex];
            }
        }
        if (pidfMode) {
            if (master.getOpMode().gamepad1.yWasPressed()) {
                if (targetVel == highVel) {
                    targetVel = lowVel;
                }

                else {
                    targetVel = highVel;
                }
            }

            if (master.getOpMode().gamepad1.bWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }



            if (master.getOpMode().gamepad1.dpadRightWasPressed()) {
                F += stepSizes[stepIndex];
            }
            if (master.getOpMode().gamepad1.dpadLeftWasPressed()) {
                F -= stepSizes[stepIndex];
            }


            if (master.getOpMode().gamepad1.dpadUpWasPressed()) {
                P += stepSizes[stepIndex];
            }
            if (master.getOpMode().gamepad1.dpadDownWasPressed()) {
                P -= stepSizes[stepIndex];
            }


            // set new PIDF coefficients
            PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
            Flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
            Flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        }
        // set velocity
        Flywheel1.setVelocity(targetVel);
        Flywheel2.setVelocity(targetVel2);

        double currentVel = Flywheel2.getVelocity();
        double error = targetVel - currentVel;

        master.getOpMode().telemetry.addData("Press A to switch between PIDF and VEL. PIDF Mode", pidfMode);
        master.getOpMode().telemetry.addData("Target Velocity", targetVel);
        master.getOpMode().telemetry.addData("Current Velocity", currentVel);
        master.getOpMode().telemetry.addData("Error", "%.2f", error);
        master.getOpMode().telemetry.addData("-------------------", 0);
        master.getOpMode().telemetry.addData("Tuning P or Flywheel1 Vel" , "%.4f (D-Pad U/D", P);
        master.getOpMode().telemetry.addData("Tuning F or Flywheel2 Vel", "%.4f (D-Pad L/R", F);
        master.getOpMode().telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
    }

}