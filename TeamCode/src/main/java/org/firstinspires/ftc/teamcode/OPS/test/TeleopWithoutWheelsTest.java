package org.firstinspires.ftc.teamcode.OPS.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;

@TeleOp(name = "Daiwik's fatahh projecting")
public class TeleopWithoutWheelsTest extends OpMode {

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


    public void loop() {

        if (!pidfMode) {
            if (gamepad1.aWasPressed()) {
                pidfMode = !pidfMode;
            }
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
        }

        if (pidfMode) {
            if (gamepad1.bWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
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
        telemetry.addLine("Left Bumper/Trigger for FLywheel1, Right Bumper/Trigger for FLywheel2");
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

        telemetry.addLine("Intake is DPAD Up/Down, Rollers is DPAD Left/RIght");
        telemetry.addData("Intake Target Power", targetPow);
        telemetry.addData("Intake Current Power", currentIntakePow);
        telemetry.addData("-------------------", 0);
        telemetry.addData("Rollers Target Power", targetPow2);
        telemetry.addData("Rollers Current Power", currentRollersPow);
        telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
    }
}
