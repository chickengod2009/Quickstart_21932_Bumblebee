package org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Master;

public class Squinting {
    Webcam webcam;
    Drivetrain drive;
    private double bearingRange = 10, correctionSpeed = 1, pitchRange = 10, speedThresh;
    private int tagID;

    public Squinting (Master master) {
        webcam = master.get("webcam");
        drive = master.getDrivetrain();
    }

    public void aiming() {
//        if (webcam.getTagPose(tagID).bearing > bearingRange) {
//            drive.manualUpdate(0,0, -correctionSpeed);
//        }
//        else if (webcam.getTagPose(tagID).bearing < -bearingRange) {
//            drive.manualUpdate(0,0, correctionSpeed);
//        }


        Webcam.TagPose pose = webcam.getTagPose(tagID);

        if(pose == null){
            return;
        }

        double yaw = pose.yaw;


        if (Math.abs(yaw) <= bearingRange){
            return;
        }
        double minTurn =  .1;
        double dir = yaw <0 ?  1 : -1;
        double rate = .05;
        double turn = rate * yaw;
        turn = Math.max(Math.abs(turn), (minTurn)) * dir;

        drive.manualUpdate(0,0,turn);




    }

    public void adjusting() {
        //TODO: larp some speed formula to adjust based off of range (dist)
    }

    public int targeting() {
        switch (Master.getTeam()) {
            case BLUE:
                if (webcam.getTagPose(42).range < webcam.getTagPose(41).range || webcam.getTagPose(45).range < webcam.getTagPose(38).range) {
                    tagID = 44;
                }
                else {
                    tagID = 39;
                }
            break;

            case RED:
                if (webcam.getTagPose(33).range < webcam.getTagPose(34).range || webcam.getTagPose(30).range < webcam.getTagPose(37).range) {
                    tagID = 31;
                }
                else {
                    tagID = 36;
                }
            break;
        }
        return tagID;
    }

    public void correctChuddedAim() {
        targeting();
        aiming();
        adjusting();
    }
}
