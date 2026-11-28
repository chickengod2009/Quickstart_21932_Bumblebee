package org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.panels.Panels;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;

public class ShooterCamDistTest {
    static DcMotorEx Flywheel1;
    static DcMotorEx Flywheel2;
    static TelemetryManager telemetryManager;

    static Webcam webcam;
    static Intake intake;


    public static double targetVel;
    public static double targetVel2;

    static double[] stepSizes = {50.0, 10.0, 1.0, 0.5, 0.05, .005, .0005};

    static int stepIndex = 1;




    public static void init(MasterTrait master) {
        webcam = master.getWebcam();
        intake = master.getIntake();
        Flywheel1 = master.getOpMode().hardwareMap.get(DcMotorEx.class, "flyMotor");
        Flywheel2 = master.getOpMode().hardwareMap.get(DcMotorEx.class, "flyMotor2");
        Flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();

        master.getOpMode().telemetry.addLine("Init complete");
        targetVel = 0;
    }

    public static void start (MasterTrait master) {
        webcam.resumeStream();
    }

    public static void loop(MasterTrait master) {
        //get all our gamepad commands
        //set target velocity
        //update master.getOpMode().telemetry


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
            if (master.getOpMode().gamepad1.a) {
                intake.setAllPower(.4);
            } else if (master.getOpMode().gamepad1.b) {
                intake.setAllPower(-.4);
            }
            else {
                intake.setAllPower(0);
            }

        // set velocity
        Flywheel1.setVelocity(targetVel);
        Flywheel2.setVelocity(targetVel2);

        double currentVel = Flywheel2.getVelocity();
        double error = targetVel - currentVel;

        master.getOpMode().telemetry.addData("April Tag", webcam.getLatestDetections());
        master.getOpMode().telemetry.addData("Distance", webcam.getTagPose(webcam.getLatestDetections()));
        master.getOpMode().telemetry.addLine("-------------------");
        master.getOpMode().telemetry.addData("Target Velocity", targetVel);
        master.getOpMode().telemetry.addData("Current Velocity", currentVel);
        master.getOpMode().telemetry.addData("Error", "%.2f", error);
        master.getOpMode().telemetry.addData("-------------------", 0);
        master.getOpMode().telemetry.addData("Step Size", "%.4f (B Button", stepSizes[stepIndex]);
        telemetryManager.update(master.getOpMode().telemetry);
    }

    public static void end(MasterTrait master) {

    }
}
