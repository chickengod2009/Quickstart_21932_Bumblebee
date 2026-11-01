package org.firstinspires.ftc.teamcode.OPS.teleop;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;

@TeleOp(name = "test")
public class test extends OpMode{
    Follower follower;
    Intake intake;
    Master master;
    Telemetry telemetry;
    double[] stepSizes = {50.0, 10.0, 1.0, 0.5, 0.05, .005, .0005};
    private double targetVel;
    int stepIndex = 1;

    @Override
    public void init() {
        //master = new Master(this, Master.TeamColor.RED, ); //add follower
        intake = new Intake(master.get("intakemotor"));
    }


    @Override
    public void loop() {

        intake.setIntakePower(gamepad1.right_trigger);
        telemetry.addData("Intake power", gamepad1.right_trigger);

        intake.update();
        telemetry.update();
    }
}