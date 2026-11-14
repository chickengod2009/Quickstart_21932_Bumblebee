package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.api.PoseFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;

import org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs.CommonPaths;
import org.firstinspires.ftc.teamcode.subsystems.Master;

public class MainBlueAuto extends OpMode{
    CommonPaths CP;

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();



    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        CP = new CommonPaths(Master.TeamColor.BLUE);
    }
    @Override
    public void start() {
        schedule(CP.commonPaths());
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        // add your other methods needed in the loop here
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.update();
    }

}
