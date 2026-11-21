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
import static com.pedropathing.ivy.commands.Commands.branch;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.groups.Groups.sequential;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.FlowerRamp;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;

import java.util.LinkedHashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class CommonPaths {


    public enum Pos{
        ShootingClose,
        ShootingFar,
        FlowerClose,
        FlowerFar,
        ELSE
    }
    Master master;
    Follower follower;
    Squinting squinting;
    //Webcam cam;
    Gate gate;
    Intake intake;
    ColorSensors CSensors;
    FlowerRamp ramp;
    Timer flowerTimer, shootTimer;
    public boolean shooting;
    private final PoseFactory poseFactoryBlue = PoseFactory.degrees();
    // Poses
    private final Pose startPose;

    //BLUE POSES
    private final Pose bluePose = poseFactoryBlue.of(86.7,132.4,-90);
    private final Pose scorePoseBlue1 = poseFactoryBlue.of(86.7, 116, 0);
    private final Pose scorePoseBlue2 = poseFactoryBlue.of(95.5, 23, 180);
    public  Pose parkPoseBlue = poseFactoryBlue.of(123, 46, -90);
    private final Pose gardenPoseBlue = poseFactoryBlue.of(0, 12, 0);
    private final Pose flowerPose1Blue = poseFactoryBlue.of(94.5, 13.5, -90);
    private final Pose flowerPose2Blue = poseFactoryBlue.of(127.5, 94, 0);


    //RED POSES
    private final PoseFactory poseFactoryRed = PoseFactory.degrees().mirrorX(70.75).mirrorY(70.75);
    private final Pose redPose = poseFactoryRed.of(86.7,132.4,-90); //TODO find pose
    private final Pose scorePoseRed1 = poseFactoryRed.of(86.7, 116, 0);
    private final Pose scorePoseRed2 = poseFactoryRed.of(95.5, 23, 180);
    public  Pose parkPoseRed = poseFactoryRed.of(123, 46, -90);
    private final Pose gardenPoseRed = poseFactoryRed.of(0, 12, 0);
    private final Pose flowerPose1Red = poseFactoryRed.of(94.5, 13.5, -90);
    private final Pose flowerPose2Red = poseFactoryRed.of(127.5, 94, 0);

    public Pos position = Pos.ShootingClose;

    private int failedAttempts =0;

    public CommonPaths(Master master, Master.TeamColor teamColor) {
        this.master = master;
        switch (teamColor) {
            case BLUE: startPose = bluePose; break;
            case RED: startPose = redPose; break;
            default: startPose = scorePoseBlue1;
        }
        follower = master.getFollower();

        follower.setPose(startPose);
        gate = master.getGate();
        //cam = new Webcam(master);
        CSensors = master.colorSensors;
        intake = master.getIntake();
        ramp = new FlowerRamp(master);
        shooting = false;
        flowerTimer = new Timer();
        shootTimer = new Timer();
        squinting = master.getSquinter();

        witchWichGoalRed = new LinkedHashMap<>();
        witchWichGoalRed.put(
                () -> {
                    if(squinting.seeID() >= 0 || failedAttempts >=5){
                        failedAttempts=0;
                        return true;
                    }
                    return false;
                },
                drainBalls
        );
        witchWichGoalRed.put(
                () -> {
                    if( squinting.seeID() <= -1){
                        failedAttempts++;
                        return true;
                    }
                    return false;
                }, sequential(goToNextGoal(), branch(witchWichGoalRed))

        );


    }

    Command goToNextGoal() {
        if (this.position == Pos.ShootingFar) {
            this.position = Pos.ShootingClose;
            return (Master.getTeam() == Master.TeamColor.RED) ? followLazy(this::toScore1Red) : followLazy(this::toScore1Blue);

        }else{
            this.position = Pos.ShootingFar;
            return (Master.getTeam() == Master.TeamColor.RED) ?  followLazy(this::toScore2Red) : followLazy(this::toScore2Blue);
        }
    }

    Command drainBalls = Command.build()
            .setStart(() -> {
                shooting = true;
                gate.setAllPosition("open");
                shootTimer.reset();
            })
            .setExecute(() -> {
                intake.setAllPower(1);
            })
            .setDone(() -> shootTimer.seconds() >2)
            .setEnd(endCondition -> {
                shooting = false;
                intake.setAllPower(0);
                gate.setAllPosition("close");
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

    private Path startToScoreBlue() {
        return line(startPose, scorePoseBlue1).linear(startPose, scorePoseBlue1);
    }
    private Path parkBlue(){
        return curve(follower.pose(), new Pose(133, 111.5), parkPoseBlue).tangent();
    }
    private Path toGardenBlue() {
        return curve(follower.pose(), new Pose(104, 133), gardenPoseBlue).tangent();
    }
    private Path toFlower1Blue() {
        return line(follower.pose(), flowerPose1Blue).constant(Math.toRadians(-90));
    }
    private Path toFlower2Blue() {
        return curve(follower.pose(), new Pose(119, 32), new Pose(101, 94), flowerPose2Blue);
    }
    private Path toScore1Blue() {
        return line(follower.pose(), scorePoseBlue1).tangent();
    }
    private Path toScore2Blue() {
        return line(follower.pose(), scorePoseBlue2).tangent();
    }
    private Path fromGardenBlue() {
        return curve(gardenPoseBlue, new Pose(114, 23), scorePoseBlue2).tangent();
    }
    private Path gardenShootBlue() {
        return path(toGardenBlue(), fromGardenBlue());
    }
//    private Path flowerShoot1() {
//        return path(toFlower1(), toScore2());
//    }
//    private Path flowerShoot2() {
//        return path(toFlower2(), toScore1());
//
//    }

    private Path startToScoreRed() {
        return line(startPose, scorePoseRed1).linear(startPose, scorePoseRed1);
    }
    private Path parkRed(){
        return curve(follower.pose(), poseFactoryRed.of(133, 111.5,0), parkPoseRed).tangent();
    }
    private Path toGardenRed() {
        return curve(follower.pose(), poseFactoryRed.of(104, 133,0), gardenPoseRed).tangent();
    }
    private Path toFlower1Red() {
        return line(follower.pose(), flowerPose1Red).constant(Math.toRadians(-90));
    }
    private Path toFlower2Red() {
        return curve(follower.pose(), poseFactoryRed.of(119, 32,0), poseFactoryRed.of(101, 94,0), flowerPose2Red);
    }
    private Path toScore1Red() {
        return line(follower.pose(), scorePoseRed1).tangent();
    }
    private Path toScore2Red() {
        return line(follower.pose(), scorePoseRed2).tangent();
    }
    private Path fromGardenRed() {
        return curve(gardenPoseRed, poseFactoryRed.of(114, 23,0), scorePoseRed2).tangent();
    }
    private Path gardenShootRed() {
        return path(toGardenRed(), fromGardenRed());
    }

    private final LinkedHashMap<BooleanSupplier, Command> witchWichGoalRed;

    private Command followLazy(Supplier<Path> pathSupplier) {     // java.util.function.Supplier
        return Command.build()
                .setStart(() -> follower.follow(pathSupplier.get()))
                .setDone(() -> !follower.isBusy())
                .setEnd(interrupted -> { });
    }

    public Command commonPathsBlue() {

        return sequential(
                follow(follower, startToScoreBlue()),
                drainBalls,
                followLazy(this::gardenShootBlue),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                followLazy(this::toFlower1Blue),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                followLazy(this::toScore2Blue),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                followLazy(this::toFlower2Blue),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                followLazy(this::toScore1Blue),
                //TODO: add automated pathing
                follow(follower, parkBlue())

        ).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }

    public Command commonPathsBlue_V2() {

        return sequential(
                follow(follower, startToScoreBlue()),
                drainBalls,
                follow(follower, gardenShootBlue()),
                branch(witchWichGoalRed),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower1Blue()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore2Blue()),
                branch(witchWichGoalRed),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower2Blue()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore1Blue()),
                //TODO: add automated pathing
                follow(follower, parkBlue())

        ).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }


    public Command commonPathsRed() {

        return sequential(
                follow(follower, startToScoreRed()),
                drainBalls,
                follow(follower, gardenShootRed()),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower1Red()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore2Red()),
                drainBalls,
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.DOWN)),
                follow(follower, toFlower2Red()),
                Commands.instant(() -> ramp.setRampPosition(FlowerRamp.UP)),
                follow(follower, toScore1Red()),
                //TODO: add automated pathing
                follow(follower, parkRed())

        ).setInterruptedBehavior(InterruptedBehavior.SUSPEND);
    }


    public Command commonPaths(){
        if(master != null){
            switch (Master.getTeam()){
                case RED:
                    return commonPathsRed();
                case BLUE:
                    return commonPathsBlue();
            }
        }
        return null;
    }




}
