package org.firstinspires.ftc.teamcode.control.functionality;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.control.ShootyBrain;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;

public interface MasterTrait {


    OpMode getOpMode();

    Drivetrain getDrivetrain();

    ShootyBrain getShootyBrain();

    Intake getIntake();

    <T extends HardwareDevice> T get(String name);

    default Master getMaster(){
        return null;
    }

    HardwareMap getHM();



}
