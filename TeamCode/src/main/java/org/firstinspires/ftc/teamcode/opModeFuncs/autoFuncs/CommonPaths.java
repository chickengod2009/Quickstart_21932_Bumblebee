package org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.groups.Groups.sequential;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;

public class CommonPaths {
    Follower follower;
    Squinting squinting;
    Webcam cam;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    // Poses
    private final Pose startPose;
    private final Pose bluePose = poseFactory.of(0,0,0); //TODO find pose
    private final Pose redPose = poseFactory.of(0,0,0); //TODO find pose


    private final Pose scorePose = poseFactory.of(48, 48, 90);
    private final Pose parkPose = poseFactory.of(72, 48, 90);
    private final Pose gardenPose = poseFactory.of(0, 12, 90);
    private final Pose flowerPose1 = poseFactory.of(0, 22, 180);
    private final Pose flowerPose2 = poseFactory.of(22, 140, 90);


    public CommonPaths(Master.TeamColor teamColor) {
        switch (teamColor) {
            case BLUE: startPose = bluePose; break;
            case RED: startPose = redPose; break;
            default: startPose = scorePose;
        }
        follower = Constants.create(hardwareMap);
        if(Master.isNull(follower)){
            throw new RuntimeException();
        }
        follower.setPose(startPose);
    }

    Command drainBalls = Command.build()
            .setStart(() -> {
                cam.resumeStream();
            })
            .setExecute(() -> {
                squinting.correctChuddedAim();
            })
            .setDone(() -> !follower.isBusy())
            .setEnd(endCondition -> {
                cam.stopStream();
            });

    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }
    private Path park(){
        return line(scorePose, parkPose).linear(scorePose, parkPose);
    }
    private Path toGarden() {
        return line(follower.pose(), gardenPose).linear(follower.pose(), gardenPose);
    }
    private Path toFlower1() {
        return line(follower.pose(), flowerPose1).linear(follower.pose(), flowerPose1);
    }
    private Path toFlower2() {
        return line(follower.pose(), flowerPose2).linear(follower.pose(), flowerPose2);
    }
    private Path toScore() {
        return line(follower.pose(), scorePose).linear(follower.pose(), scorePose);
    }
    private Path gardenShoot() {
        return path(toGarden(), toScore());
    }
    private Path flowerShoot1() {
        return path(toFlower1(), toScore());
    }
    private Path flowerShoot2() {
        return path(toFlower2(), toScore());

    }

    private Command commonPaths() {
        return sequential(
                follow(follower, startToScore()),
                drainBalls,
                follow(follower, gardenShoot()),
                drainBalls,
                follow(follower, flowerShoot1()),
                drainBalls,
                follow(follower, flowerShoot2()),
                drainBalls,
                //TODO: add automated pathing
                follow(follower, park())
        );
    }
}
