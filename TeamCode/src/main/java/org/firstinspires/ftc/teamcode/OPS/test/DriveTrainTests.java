package org.firstinspires.ftc.teamcode.OPS.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Util;


@TeleOp(name = "TestStuff", group = "Test")
public class DriveTrainTests extends OpMode {

  Util util;


  @Override
  public void init() {
    this.telemetry.addLine("Select opmode: \n X: <>, Y: <> "/*ETC*/);

    OpModeFunctions fun = null;

    boolean done =false;

    do{

      if(gamepad1.a){done = true; fun = OpModeFunctions.TEST_1;}


    }while(!done);

    util = new Util(this, Util.TeamColor.RED, fun);


  }

  @Override
  public void start(){
    util.start();

  }


  @Override
  public void loop(){
    util.update();
  } 

  @Override
  public void stop() {

  } 






}
