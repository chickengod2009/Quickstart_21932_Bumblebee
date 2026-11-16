package org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.utils.Timer;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.groups.Groups.sequential;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.FlowerRamp;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;

import java.util.LinkedHashMap;
import java.util.function.BooleanSupplier;

public class CommonPaths {
    Master master;
    Follower follower;
    Squinting squinting;
    Webcam cam;
    Gate gate;
    Intake intake;
    ColorSensors CSensors;
    FlowerRamp ramp;
    Timer flowerTimer;
    public boolean shooting;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    // Poses
    private final Pose startPose;
    private final Pose bluePose = poseFactory.of(86.7,132.4,-90);
    private final Pose redPose = poseFactory.of(0,0,0); //TODO find pose


    private final Pose scorePoseBlue1 = poseFactory.of(86.7, 116, 0);
    private final Pose scorePoseBlue2 = poseFactory.of(95.5, 23, 180);
    private final Pose parkPose = poseFactory.of(123, 46, -90);
    private final Pose gardenPose = poseFactory.of(0, 12, 0);
    private final Pose flowerPose1 = poseFactory.of(94.5, 13.5, -90);
    private final Pose flowerPose2 = poseFactory.of(127.5, 94, 0);


    public CommonPaths(Master.TeamColor teamColor) {
        switch (teamColor) {
            case BLUE: startPose = bluePose; break;
            case RED: startPose = redPose; break;
            default: startPose = scorePoseBlue1;
        }
        follower = master.getFollower();

        follower.setPose(startPose);
        gate = new Gate(master);
        cam = new Webcam(master);
        CSensors = master.colorSensors;
        intake = master.getIntake();
        ramp = new FlowerRamp(master);
        shooting = false;
        flowerTimer = new Timer();
    }

    Command drainBalls = Command.build()
            .setStart(() -> {
                shooting = true;
                gate.setPosition(Gate.OPEN);
            })
            .setExecute(() -> {
                intake.setAllPower(1);
            })
            .setDone(() -> !CSensors.bladderFullOfPollen() && !CSensors.bladderFullOfNectar())
            .setEnd(endCondition -> {
                shooting = false;
                intake.setAllPower(0);
                gate.setPosition(Gate.CLOSE);
            });
//    Command operateRamp = Command.build()
//            .setStart(() -> {
//                flowerTimer.reset();
//                ramp.setRampPosition(FlowerRamp.DOWN);
//            })
//            .setDone(() -> flowerTimer.seconds() == 5)
//            .setEnd(endCondition -> {
//                ramp.setRampPosition(FlowerRamp.UP);
//            });

    private Path startToScore() {
        return line(startPose, scorePoseBlue1).linear(startPose, scorePoseBlue1);
    }
    private Path park(){
        return curve(follower.pose(), new Pose(133, 111.5), parkPose).tangent();
    }
    private Path toGarden() {
        return curve(follower.pose(), new Pose(104, 133), gardenPose).tangent();
    }
    private Path toFlower1() {
        return line(follower.pose(), flowerPose1).constant(-90);
    }
    private Path toFlower2() {
        return curve(follower.pose(), new Pose(119, 32), new Pose(101, 94), flowerPose2);
    }
    private Path toScore1() {
        return line(follower.pose(), scorePoseBlue1).tangent();
    }
    private Path toScore2() {
        return line(follower.pose(), scorePoseBlue2).tangent();
    }
    private Path fromGarden() {
        return curve(gardenPose, new Pose(114, 23), scorePoseBlue2).tangent();
    }
    private Path gardenShoot() {
        return path(toGarden(), fromGarden());
    }
//    private Path flowerShoot1() {
//        return path(toFlower1(), toScore2());
//    }
//    private Path flowerShoot2() {
//        return path(toFlower2(), toScore1());
//
//    }

    private static final LinkedHashMap<BooleanSupplier, Command> witchWichGoal;



    public Command commonPaths() {

        return sequential(
                follow(follower, startToScore()),
                drainBalls,
                follow(follower, gardenShoot()),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower1()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore2()),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower2()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore1()),
                //TODO: add automated pathing
                follow(follower, park())

        ).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }



    static {
        witchWichGoal  = new LinkedHashMap<>();
        witchWichGoal.put(
                () -> {
                    throw Master.TODO();
                },

                sequential()

        );



    }
}
