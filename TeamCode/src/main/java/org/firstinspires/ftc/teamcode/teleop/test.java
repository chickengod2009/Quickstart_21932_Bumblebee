package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Util;

@TeleOp
public class test extends OpMode{
    Intake intake;
    Util util;
    Telemetry telemetry;
    double[] stepSizes = {50.0, 10.0, 1.0, 0.5, 0.05, .005, .0005};
    private double targetVel;
    int stepIndex = 1;

    @Override
    public void init() {
        util = new Util(this);
        intake = new Intake(util.get("intakemotor"));
    }


    @Override
    public void loop() {

        intake.setIntakePower(gamepad1.right_trigger);
        telemetry.addData("Intake power", gamepad1.right_trigger);

        intake.update();
        telemetry.update();
    }
}