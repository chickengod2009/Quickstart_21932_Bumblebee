package org.firstinspires.ftc.teamcode.OPS.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Util;
@TeleOp(name = "TelopRed", group = "Teleops")
public class TelopRed extends OpMode {


  Util util;

  public void init() {
    util = new Util(this, Util.TeamColor.RED, OpModeFunctions.TELOP);

  }

  public void start(){util.start();}

  public void loop(){
    util.update();

  }

  public void stop(){
    util.stop();
  }  


}
