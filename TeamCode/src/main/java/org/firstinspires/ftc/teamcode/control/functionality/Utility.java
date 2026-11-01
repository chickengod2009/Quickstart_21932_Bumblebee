package org.firstinspires.ftc.control.functionality;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.control.ShootyBrain;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public interface Utility {

    ShootyBrain getShootyBrain();

    Intake getIntake();

    Drivetrain getDrive();

    OpMode getOpmode();




}