package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Util;

class TelopRed extends OpMode {


  Util util;

  public void init() {
    util = new Util(this, Util.TeamColor.RED, OpModeFunctions.Telop);
    util.start();
  }

  public void start(){}

  public void loop(){
    util.update();

  }

  public void stop(){
    util.stop();
  }  


}
