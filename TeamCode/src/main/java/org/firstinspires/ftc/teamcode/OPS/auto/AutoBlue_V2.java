package org.firstinspires.ftc.teamcode.OPS.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;

@Autonomous(name = "AutoBlue_V27878787", group = "Autos")
public class AutoBlue_V2 extends OpMode {


    Master master;

    public void init() {
        master = new Master(this, Master.TeamColor.BLUE, OpModeFunctions.AUTON_TWO);
        master.init();
    }

    public void start(){
        master.start();
    }

    public void loop(){
        master.update();

    }

    public void stop(){
        master.stop();
    }

}
