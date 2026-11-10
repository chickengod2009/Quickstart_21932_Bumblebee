package org.firstinspires.ftc.teamcode.OPS.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;


@TeleOp(name = "Almond's a little femboy", group = "Test")
public class DriveTrainTests extends OpMode {

  Master master;

  OpModeFunctions func = OpModeFunctions.Intake_Gate_Shooter_Test;


  @Override
  public void init() {







    this.telemetry.addLine("I LIVE"/*ETC*/);
    this.telemetry.update();


  }

  @Override
  public void init_loop() {
    this.telemetry.addLine("Select opmode: \n A: Full Test, Y: Intake -- Gate --- Shooter Test "/*ETC*/);


    if (gamepad1.aWasPressed()){
      func = OpModeFunctions.FULL_TEST;
    }else if (gamepad1.bWasPressed()){
      func = OpModeFunctions.Intake_Gate_Shooter_Test;
    }

    master = new Master(this, Master.TeamColor.RED, func);
    this.telemetry.update();


  }


  @Override
  public void start(){

    master.start();


    telemetry.update();

  }


  @Override
  public void loop(){
    if(master != null) master.update();
  } 

  @Override
  public void stop() {
//    master.stop();
  }






}
