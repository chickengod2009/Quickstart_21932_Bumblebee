package org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;


public class FullTest{
    public static DcMotorEx Flywheel1;
    public static DcMotorEx Flywheel2;

    public static double lowVel = 1500;

    public static double highVel = 2100;

    static double targetVel;
    static double targetVel2;
    static boolean pidfMode;

    static double F = 0;
    static double P = 0;
    public static DcMotorEx intake;
    public static DcMotorEx rollers;


    static double  targetPow;
    static double targetPow2;


    static double[] stepSizes = {1000.0, 100.0, 50.0, 10.0, 1.0, 0.5, 0.05, .005, .0005};

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

        Flywheel1 = hardwareMap.get(DcMotorEx.class, "flyMotor");
        Flywheel2 = hardwareMap.get(DcMotorEx.class, "flyMotor2");
        Flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        Flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        Flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init complete");
        pidfMode = false;
        targetVel = 0;
    }



    public static void loop(MasterTrait master) {
        //get all our gamepad commands
        //set target velocity
        //update master.getOpMode().telemetry



        if (!pidfMode) {
            if (gamepad1.bWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }
            if (gamepad1.leftBumperWasPressed()) {
                targetVel += stepSizes[stepIndex];
            }
            if (gamepad1.leftTriggerWasPressed()) {
                targetVel -= stepSizes[stepIndex];
            }
            if (gamepad1.rightBumperWasPressed()) {
                targetVel2 += stepSizes[stepIndex];
            }
            if (gamepad1.rightTriggerWasPressed()) {
                targetVel2 -= stepSizes[stepIndex];
            }
        }
        if (pidfMode) {
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

            if (gamepad1.aWasPressed()) {
                pidfMode = !pidfMode;
            }

            if (gamepad1.yWasPressed()) {
                if (targetVel == highVel) {
                    targetVel = lowVel;
                }

                else {
                    targetVel = highVel;
                }
            }

            if (gamepad1.bWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }



            if (gamepad1.dpadRightWasPressed()) {
                F += stepSizes[stepIndex];
            }
            if (gamepad1.dpadLeftWasPressed()) {
                F -= stepSizes[stepIndex];
            }


            if (gamepad1.dpadUpWasPressed()) {
                P += stepSizes[stepIndex];
            }
            if (gamepad1.dpadDownWasPressed()) {
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

        double currentVel = Flywheel1.getVelocity();
        double currentVel2 = Flywheel2.getVelocity();
        double error = targetVel - currentVel;

        telemetry.addData("Press A to switch between PIDF and VEL. PIDF Mode", pidfMode);
        telemetry.addData("Target Velocity1", targetVel);
        telemetry.addData("Target Velocity2", targetVel2);
        telemetry.addData("Current Velocity1", currentVel);
        telemetry.addData("Current Velocity2", currentVel2);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("-------------------", 0);
        telemetry.addData("Tuning P or Flywheel1 Vel" , "%.4f (D-Pad U/D", P);
        telemetry.addData("Tuning F or Flywheel2 Vel", "%.4f (D-Pad L/R", F);
        telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);



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


