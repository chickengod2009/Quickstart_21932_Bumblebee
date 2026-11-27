package org.firstinspires.ftc.teamcode.opModeFuncs.telopFuncs;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.commands.Commands;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.control.ShootyBrain;
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
//@Configurable
public class TeleopFunctions {
  private static MasterTrait master;
  private static Follower follower;
  static ColorSensors CSensors;
  static Drivetrain drive;
  static FlowerRamp ramp;
  //static Gate gate;
  static Intake intake;
  static ShootyBrain shooter;
  static Webcam cam;
  static Squinting squinting;
  static Gate gate;
  static TelemetryManager telemetryManager;
  private static double driveSpeed;

  private enum STATE {DRIVING, PARKING, DYING}

  private static STATE state;


  public static void init(MasterTrait master) {
    //TeleopFunctions.master = master;
    follower = master.getFollower();
    follower.setPose(new Pose(123, 46, Math.toDegrees(-90)));
    CSensors = master.getColorSensors();
    drive = master.getDrivetrain();
    TeleopFunctions.ramp = new FlowerRamp(master.getMaster());
    gate = new Gate(master.getMaster());
    TeleopFunctions.intake = master.getIntake();
    TeleopFunctions.shooter = master.getShootyBrain();
    cam = master.getWebcam();
    TeleopFunctions.squinting = master.getSquinter();
    TeleopFunctions.telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
    TeleopFunctions.driveSpeed = 1;
    TeleopFunctions.state = STATE.DRIVING;

  }


  public static void start(MasterTrait master) {
    shooter.setOverloadPollen(1850.0);
    shooter.setOverloadNectar(2000.0);
    master.getOpMode().telemetry.addLine(shooter.log());
    shooter.forceThink();

    cam.stopStream();
    TeleopFunctions.ramp.setRampPosition(FlowerRamp.UP);
  }


  public static void update(MasterTrait master) {
    intake.setAllPower(0);
    shooter.setOverloadPollen(pollenvectoconf.pollen);
    shooter.setOverloadNectar(pollenvectoconf.nectar);
    TeleopFunctions.drive.setDrive(master.getOpMode().gamepad1.left_stick_x, master.getOpMode().gamepad1.left_stick_y);
    TeleopFunctions.drive.setAimTurn(master.getOpMode().gamepad1.right_stick_x, 5);
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
        if (master.getOpMode().gamepad1.leftTriggerWasReleased()) {
          cam.stopStream();
        }

        if (master.getOpMode().gamepad1.right_trigger > .5) {
          shooter.activate();
          telemetryManager.addLine("Activated");
        }
        else{
          shooter.deactivate();

        }

//      ------FLOWER------
        if (master.getOpMode().gamepad2.dpadDownWasPressed()) {
          ramp.setRampPosition(FlowerRamp.DOWN);
        }
        if (master.getOpMode().gamepad2.dpadUpWasPressed()) {
          ramp.setRampPosition(FlowerRamp.UP);
        }


//       ------INTAKE------
        if (master.getOpMode().gamepad1.right_bumper) {
          intake.setAllPower(Intake.intakeSpeed);
        }
        if (master.getOpMode().gamepad1.left_bumper) {
          intake.setAllPower(0);
        }

//      ------EJECT------
        if (master.getOpMode().gamepad2.a) {
          intake.setRollerPower(-.2);
        }
        if (master.getOpMode().gamepad2.b) {
          intake.setIntakePower(-.2);
        }
        if (master.getOpMode().gamepad2.y) {
          shooter.setOverloadNectar(-100.0);
          shooter.setOverloadPollen(-100.0);
        }



//      ------LOCALIZE------
        if (master.getOpMode().gamepad2.leftTriggerWasPressed() && master.getOpMode().gamepad2.rightTriggerWasPressed()) {
          follower.setPose(new Pose(7.8, 8.97, -90));
        } // bottom right corner
        break;

      case PARKING:
        driveSpeed = Intake.intakeSpeed;
        break;
    }
//  ------UPDATE------
    CSensors.update();
    ramp.update();
    drive.update();
    intake.update();
    shooter.update();
    follower.update();

//  ------TELEMETRY------
    master.getOpMode().telemetry.addData("STATE", state);
    master.getOpMode().telemetry.addLine("------SHOOTER------");
    master.getOpMode().telemetry.addData("Pollen Vel", shooter.getMortar().getPollenTargetVelocity());
    master.getOpMode().telemetry.addData("Nectar Vel", shooter.getMortar().getNectarTargetVelocity());
    master.getOpMode().telemetry.addLine("------INTAKE------");
    master.getOpMode().telemetry.addData("Intake Power", intake.getIntakePower());
    master.getOpMode().telemetry.addData("Rollers Power", intake.getRollersPower());
    master.getOpMode().telemetry.addLine("------FLOWER------");
    master.getOpMode().telemetry.addData("Ramp Position", ramp.getRampPosition());
    master.getOpMode().telemetry.addLine("------GATE------");
    master.getOpMode().telemetry.addData("Gate Positions", shooter.getGate().getPosition());
    master.getOpMode().telemetry.addLine("------COLOR SENSORS------");
    master.getOpMode().telemetry.addData("Top Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.ONE)));
    //master.getOpMode().telemetry.addData("Middle Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.TWO)));
    master.getOpMode().telemetry.addData("Bottom Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.THREE)));
    master.getOpMode().telemetry.addData("Top Nectar Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.FIVE)));
    master.getOpMode().telemetry.addData("Bottom Nectar/Pollen Sensor", CSensors.hasBall((ColorSensors.RGB.SensorID.FOUR)));
    master.getOpMode().telemetry.addLine("------POSE------");
    master.getOpMode().telemetry.addData("Robot Pose", follower.pose());

    master.getOpMode().telemetry.update();
    telemetryManager.update(master.getOpMode().telemetry);

  }


  public static void end(MasterTrait master) {
    shooter.off();

  }
}




