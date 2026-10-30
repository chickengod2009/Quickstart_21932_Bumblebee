package org.firstinspires.ftc.teamcode.OPS.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Util;

public class DriveTrainTests extends OpMode {

  Util util;


  @Override
  public void init() {

  }

  @Override
  public void start(){

    this.telemetry.addLine("Select opmode: \n X: <>, Y: <> "/*ETC*/);

    OpModeFunctions fun = null;

    do{

      //check what button was pressed
    

    }while(false);

    util = new Util(this, Util.TeamColor.RED, fun);

    util.start();
  }


  @Override
  public void loop(){
    util.update();
  } 

  @Override
  public void stop(){
    util.stop();
  } 






}
