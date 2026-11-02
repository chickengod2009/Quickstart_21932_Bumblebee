package org.firstinspires.ftc.teamcode.OPS.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;


@TeleOp(name = "Test Stuff Please Work", group = "Test")
public class DriveTrainTests extends OpMode {

  Master master;


  @Override
  public void init() {
    this.telemetry.addLine("Select opmode: \n X: <>, Y: <> "/*ETC*/);
    this.telemetry.update();

    OpModeFunctions fun = OpModeFunctions.TEST_1;


    master = new Master(this, Master.TeamColor.RED, fun);
    this.telemetry.addLine("I LIVE"/*ETC*/);
    this.telemetry.update();


  }

  @Override
  public void start(){
    if(master != null) master.start();
    else {
      telemetry.addLine("Init is second btw");
    }

    telemetry.update();

  }


  @Override
  public void loop(){
    if(master != null) master.update();
  } 

  @Override
  public void stop() {
    master.stop();
  } 






}
