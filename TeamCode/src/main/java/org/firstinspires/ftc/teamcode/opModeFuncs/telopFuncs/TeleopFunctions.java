package org.firstinspires.ftc.teamcode.opModeFuncs.telopFuncs;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.control.functionality.MasterTrait;
import org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs.CommonPaths;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlowerRamp;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class TeleopFunctions {
  private static MasterTrait master;
  private static Follower follower;
  static ColorSensors CSensors;
  static Drivetrain drive;
  static FlowerRamp ramp;
  static Gate gate;
  static Intake intake;
  static Shooter shooter;
  static Webcam cam;
  static Squinting squinting;
  static TelemetryManager telemetryManager;
  private static double driveSpeed;

  private enum STATE {DRIVING, PARKING, DYING}

  private static STATE state;


  public static void init(MasterTrait master) {
    TeleopFunctions.master = master;
    follower = master.getFollower();
    follower.setPose(new Pose(123, 46, -90));
    CSensors = master.getColorSensors();
    drive = master.getDrivetrain();
    ramp = new FlowerRamp(master.getMaster());
    gate = new Gate(master.getMaster());
    intake = master.getIntake();
    shooter = new Shooter(master.getMaster());
    cam = new Webcam(master.getMaster());
    squinting = new Squinting(master.getMaster());
    telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
    driveSpeed = 1;
    state = STATE.DRIVING;
  }

  public static void start(MasterTrait master) {
    shooter.setPollenVel(2350);
    shooter.setNectarVel(2500);
    ramp.setRampPosition(FlowerRamp.UP);
  }


  public static void update(MasterTrait master) {
    follower.manual(-master.getOpMode().gamepad1.left_stick_y * driveSpeed, -master.getOpMode().gamepad1.left_stick_x * driveSpeed, -master.getOpMode().gamepad1.right_stick_x * driveSpeed);
    if (master.getOpMode().gamepad2.rightTriggerWasPressed()) {
      state = STATE.DRIVING;
    }
    if (master.getOpMode().gamepad2.leftTriggerWasPressed()) {
      state = STATE.PARKING;
    }
    switch (state) {
      case DRIVING:
        driveSpeed = 1;

//      ------SHOOTING------
        if (master.getOpMode().gamepad1.leftTriggerWasPressed()) {
          cam.resumeStream();
          squinting.correctChuddedAim();
        }
        if (master.getOpMode().gamepad2.rightTriggerWasReleased()) {
          cam.stopStream();
        }
        if (master.getOpMode().gamepad1.rightTriggerWasPressed()) {
          gate.setPosition(Gate.OPEN);
          Commands.waitMs(500);
          intake.setAllPower(.5);
        }

//      ------FLOWER------
        if (master.getOpMode().gamepad2.dpadDownWasPressed()) {
          ramp.setRampPosition(FlowerRamp.DOWN);
        }
        if (master.getOpMode().gamepad2.dpadUpWasPressed()) {
          ramp.setRampPosition(FlowerRamp.UP);
        }

//      ------INTAKE------
        if (master.getOpMode().gamepad1.rightTriggerWasPressed()) {
          intake.setAllPower(.5);
        }
        if (master.getOpMode().gamepad1.leftTriggerWasPressed()) {
          intake.setAllPower(0);
        }

//      ------EJECT------
        if (master.getOpMode().gamepad2.aWasPressed()) {
          intake.setAllPower(-.2);
        }
        if (master.getOpMode().gamepad2.bWasPressed()) {
          intake.setAllPower(0);
        }

//      ------LOCALIZE------
        if (master.getOpMode().gamepad2.leftTriggerWasPressed() && master.getOpMode().gamepad2.rightTriggerWasPressed()) {
          follower.setPose(new Pose(7.8, 8.97, -90));
        } // bottom right corner

      case PARKING:
        driveSpeed = .5;
    }
//  ------UPDATE------
    CSensors.update();
    ramp.update();
    gate.update();
    intake.update();
    shooter.update();
    follower.update();

//  ------TELEMETRY------
    telemetryManager.addData("STATE", state);
    telemetryManager.addLine("------SHOOTER------");
    telemetryManager.addData("Pollen Vel", shooter.getPollenTargetVelocity());
    telemetryManager.addData("Nectar Vel", shooter.getNectarTargetVelocity());
    telemetryManager.addLine("------INTAKE------");
    telemetryManager.addData("Intake Power", intake.getIntakePower());
    telemetryManager.addData("Rollers Power", intake.getRollersPower());
    telemetryManager.addLine("------FLOWER------");
    telemetryManager.addData("Ramp Position", ramp.getRampPosition());
    telemetryManager.addLine("------GATE------");
    telemetryManager.addData("Gate Positions", gate.getPosition());
    telemetryManager.addLine("------COLOR SENSORS------");
    telemetryManager.addData("Top Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.ONE)));
    telemetryManager.addData("Middle Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.TWO)));
    telemetryManager.addData("Bottom Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.THREE)));
    telemetryManager.addData("Top Nectar Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.FIVE)));
    telemetryManager.addData("Bottom Nectar/Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.FOUR)));
    telemetryManager.addLine("------POSE------");
    telemetryManager.addData("Robot Pose", follower.pose());

    telemetryManager.update();

  }


  public static void end(MasterTrait master) {


  }
}




