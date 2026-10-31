package org.firstinspires.ftc.teamcode.OPS.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;


@TeleOp(name = "TesgtStuff", group = "Test")
public class DriveTrainTests extends OpMode {

  Master master;


  @Override
  public void init() {
    //this.telemetry.addLine("Select opmode: \n X: <>, Y: <> "/*ETC*/);

    OpModeFunctions fun = OpModeFunctions.TEST_1;





    master = new Master(this, Master.TeamColor.RED, fun);


  }

  @Override
  public void start(){
    OpModeFunctions fun = OpModeFunctions.TEST_1;





    //util = new Util(this, Util.TeamColor.RED, fun);
//    try {
//      util.start();
//    }catch (Exception e){telemetry.addLine(":("); telemetry.update(); requestOpModeStop();}

  }


  @Override
  public void loop(){
//    try{
//    util.update();
//    }catch (Exception e){telemetry.addLine(":( 2"); telemetry.update(); requestOpModeStop();}
    telemetry.update();
  } 

  @Override
  public void stop() {

  } 






}
