package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import java.util.HashMap;


public class Shooter {
    private DcMotorEx flyMotor, flyMotor2;

    private double power, vel;

    public static double THRESH = 25;
    public static double OFF = 0, MAX = 1, NORMAL = 0.6, WAIT = 1350, WAITF = 1800;
    public static double slope = 5.9343, closeB = 955, farB = 1055;
    public static double p = 200, i = 0, d = 0, f = 13;


    public Shooter(HardwareMap hardwareMap, HashMap<String, String> config) {
        flyMotor = hardwareMap.get(DcMotorEx.class, "flyMotor");
        flyMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flyMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, i, d, f));
        flyMotor.setDirection(Direction.REVERSE);

        flyMotor2 = hardwareMap.get(DcMotorEx.class, "flyMotor2");
        flyMotor2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flyMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, i, d, f));
        flyMotor2.setDirection(Direction.REVERSE);
    }
    public void setPower(double motorPower) {
        power = motorPower;
    }

    public void setVelocity(double velocity) {
        vel = velocity;
    }

    public double getVelocity() {
        return flyMotor2.getVelocity();
    }

    /*
    public int calcVelocity(double dist) {
        double b = dist>120 ? farB : closeB;
        //b = closeB;
        return (int) (slope*(dist) + b);
    }
    */


    public void setFlyMotorPower(double power) {
        flyMotor.setPower(power);
    }
    public void setFlyMotor2Power(double power) {
        flyMotor2.setPower(power);
    }

    public double getTargetVelocity() {
        return vel;
    }

    public boolean safeToShoot(int target) { return (getVelocity() > target - Shooter.THRESH && getVelocity() < target + Shooter.THRESH && getVelocity() <= target); }
    public double getPower() {
        return power;
    }
    /*
        public double calcFlywheel(double hoodAngle, double x) {
            return 300 * calcExitVel(hoodAngle, x) - 800;
        }

        public double calcExitVel(double hoodAngle, double x) {
            return Math.sqrt(g*x*x/(2*Math.cos(hoodAngle)*Math.cos(hoodAngle)*(x*Math.tan(hoodAngle) - yDistToGoal)));
        }
    */
    public void update() {
        flyMotor.setPower(power);
        flyMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, 0, 0, f));
        flyMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, 0, 0, f));
        flyMotor.setVelocity(vel);
        flyMotor2.setVelocity(vel);
    }
}