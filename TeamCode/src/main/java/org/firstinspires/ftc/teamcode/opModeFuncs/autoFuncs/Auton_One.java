package org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.control.AutonPackage;
import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;

import static com.pedropathing.ivy.Scheduler.schedule;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class Auton_One {
    private static MasterTrait master;
    static CommonPaths CP;
    static ColorSensors CSensors;
    static Intake intake;
    static Shooter shooter;
    static private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    public final Pose endPose = poseFactory.of(123, 46, -90);



    public static void init(MasterTrait master) {
        Auton_One.master = master;
        Scheduler.reset();
        follower = master.getFollower();
        CP = new CommonPaths(master.getMaster(), Master.getTeam());
        CSensors = master.getColorSensors();
        intake = master.getIntake();
        shooter = new Shooter(master.getMaster());
    }

    public static void start(MasterTrait master) {
        shooter.setPollenVel(2350);
        shooter.setNectarVel(2500);
        schedule(CP.commonPaths());
    }

    public static void loop(MasterTrait master) {
//        if (!CP.shooting || CSensors.bladderFullOfPollen() || CSensors.bladderFullOfNectar()) {
//            intake.setAllPower(0);
//        }
//        else {
//            intake.setAllPower(Intake.intakeSpeed);
//        }
        follower.update();
        Scheduler.execute();
        // add your other methods needed in the loop here
        master.getOpMode().telemetry.addData("X", follower.pose().x());
        master.getOpMode().telemetry.addData("Y", follower.pose().y());
        master.getOpMode().telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        master.getOpMode().telemetry.addData("Follower Mode", follower.mode());
        master.getOpMode().telemetry.update();
    }


   public static void fin(MasterTrait master) {
       AutonPackage.assignVariables(master.getFollower().pose());
   }

}
