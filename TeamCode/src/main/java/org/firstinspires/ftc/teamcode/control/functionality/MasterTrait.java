package org.firstinspires.ftc.teamcode.control.functionality;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.control.ShootyBrain;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public interface MasterTrait {


    OpMode getOpMode();

    Drivetrain getDrivetrain();

    ShootyBrain getShootyBrain();

    Intake getIntake();

    <T extends HardwareDevice> T get(String name);



}
