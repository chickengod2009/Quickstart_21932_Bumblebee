package org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam;

import android.util.Size;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;
import java.util.List;

public class Webcam {

    private final VisionPortal visionPortal;

    private final AprilTagProcessor aprilTagProcessor;
    private List<AprilTagDetection> aprilTagDetections;
    private int aprilTag;


    public final int cameraWidth = 640, cameraHeight = 480; // TODO: set proper aspect ratio and resolution if needed


    public Webcam(HardwareMap hwMap, HashMap<String, String> config) {

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, config.get("webcam1")))
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(cameraWidth, cameraHeight))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();
    }

    public List<AprilTagDetection> detectAprilTags() {
        aprilTagDetections = aprilTagProcessor.getFreshDetections();
        return aprilTagDetections;
    }

    public int getLatestDetections() {
        detectAprilTags();
        if (aprilTagDetections != null) {
            for(AprilTagDetection aprilTagDetection : aprilTagDetections) {
                if (aprilTagDetection.metadata != null) {
                    aprilTag = aprilTagDetection.id;
                }
            }
        }
        else {
            aprilTag = -1;
        }
        return aprilTag;
    }

    public void stopStream() {
        if (visionPortal != null) {
            visionPortal.stopStreaming();
        }
    }
    public void resumeStream() {
        if (visionPortal != null) {
            visionPortal.resumeStreaming();
        }
    }
    public static class TagPose {
        public final int id;
        public final double yaw;     //tag rotation relative to camera (deg)
        public final double range;   //straight line distance to tag (in.)
        public final double bearing; //angle to tag (degrees). positive = tag is to the left

        public TagPose(int id, double yaw, double range, double bearing) {
            this.id = id;
            this.yaw = yaw;
            this.range = range;
            this.bearing = bearing;
        }
    }

    public TagPose getTagPose(int tagId) {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        for (AprilTagDetection d : detections) {
            if (d.metadata != null && d.id == tagId) {
                return new TagPose(d.id, d.ftcPose.yaw, d.ftcPose.range, d.ftcPose.bearing);
            }

        }
        return null;
    }
}