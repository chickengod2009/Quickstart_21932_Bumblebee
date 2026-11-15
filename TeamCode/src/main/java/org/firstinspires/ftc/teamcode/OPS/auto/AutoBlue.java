package org.firstinspires.ftc.teamcode.OPS.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;

@Autonomous(name = "AutoBlue", group = "Autos")
public class AutoBlue extends OpMode {


    Master master;

    public void init() {
        master = new Master(this, Master.TeamColor.BLUE, OpModeFunctions.AUTON_BLUE_ONE);

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
