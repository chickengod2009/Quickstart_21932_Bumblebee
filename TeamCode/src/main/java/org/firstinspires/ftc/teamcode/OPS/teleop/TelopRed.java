package org.firstinspires.ftc.teamcode.OPS.teleop;


import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;
@Configurable
@TeleOp(name = "TelopRed", group = "Teleops")
public class TelopRed extends OpMode {


  Master master;

  public void init() {
    master = new Master(this, Master.TeamColor.RED, OpModeFunctions.TELOP);
    master.init();
  }

  public void start(){
    master.start();}

  public void loop(){
    master.update();

  }

  public void stop(){
    master.stop();
  }  


}
