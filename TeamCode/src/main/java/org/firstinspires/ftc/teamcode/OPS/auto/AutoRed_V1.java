package org.firstinspires.ftc.teamcode.OPS.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;

@Autonomous(name = "AutoRed_V1", group = "Autos")
public class AutoRed_V1 extends OpMode {


    Master master;

    public void init() {
        master = new Master(this, Master.TeamColor.RED, OpModeFunctions.AUTON_ONE);
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
