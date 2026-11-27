package org.firstinspires.ftc.teamcode.control.functionality;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.control.ShootyBrain;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.BallSniffer;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.SauronHandler;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;

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

    default ColorSensors getColorSensors(){
        return null;
    }

    SauronHandler getLLHandler();

    BallSniffer getBallSniffer();

    Follower getFollower();

    Squinting getSquinter();
    Webcam getWebcam();

    Gate getGate();



}
