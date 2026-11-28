package org.firstinspires.ftc.teamcode.OPS.test;


import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.Master;
@Configurable
@TeleOp(name = "dih-stance test", group = "Test")
public class DistTest extends OpMode {


    Master master;

    public void init() {
        master = new Master(this, Master.TeamColor.BLUE, OpModeFunctions.DIST_TEST);
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
