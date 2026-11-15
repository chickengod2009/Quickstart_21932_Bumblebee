package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.control.functionality.DefaultUpdate;
import org.firstinspires.ftc.teamcode.control.functionality.UtilityObject;

public class Drivetrain implements UtilityObject, DefaultUpdate {

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    public static double speedMult = 1;

    public final Master master;

    public static boolean test = false;

    // --- tuning ---
    private static final double DEADBAND        = 0.05;  // ignore tiny stick values
    private static final double DRIVER_TAKEOVER = 0.30;  // |rx| above this gives rotation back to the driver
    public static final double AIM_MAX_TURN    = 0.40;  // cap on auto-aim rotation

    // --- inputs (rx is counter-clockwise positive) ---
    private double x, y, rx;


    public Drivetrain(Master master) {
        this.master = master;

        frontLeft  = master.get("frontLeftMotor");
        backLeft   = master.get("backLeftMotor");
        frontRight = master.get("frontRightMotor");
        backRight  = master.get("backRightMotor");

        Master.reverse(new HardwareDevice[] {frontLeft, backLeft});

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Driver inputs. rx is counter-clockwise positive, so pass the right stick negated.
     * Call every loop.
     */
    public void setDrive(double x, double y) {
        this.x  = deadband(x);
        this.y  = deadband(y);

    }

    /**
     * Auto-aim rotation request in the same convention as rx, or NaN to release.
     * Call every loop.
     */

    int lastPriority =0;
    public final int drivePrior=5;
    public void setAimTurn(double turn, int priority) {
        if (Double.isNaN(turn)) return;
        if(priority == drivePrior) turn =deadband(turn);
        if(lastPriority< priority) {
            this.rx = turn;
            lastPriority=priority;
        }
    }

    @Override
    public void update() {
        double turn = resolveTurn();

        // Scale all wheels together so the driving direction survives when the total exceeds 1
        double denom = Math.max(1.0, Math.abs(y) + Math.abs(x) + Math.abs(turn));

        frontLeft.setPower( (y + x - turn) / denom * speedMult);
        backLeft.setPower(  (y - x - turn) / denom * speedMult);
        frontRight.setPower((y - x + turn) / denom * speedMult);
        backRight.setPower( (y + x + turn) / denom * speedMult);

        lastPriority=0;
        rx= 0;
        x=0;
        y=0;

    }


    public void manualUpdate(double xx, double yy, double rrx) {
        setDrive(xx, yy);
        int p;
        if(rrx >= DRIVER_TAKEOVER) p=1000; else p = drivePrior;
        setAimTurn(rrx, p);
        update();
    }

    public void stop() {
        x = y = rx = 0;
        lastPriority=0;
        update();
    }

    public void parkMode() {
        speedMult = .30;
    }

    public void speedMode() {
        speedMult = 1;
    }

    @Override
    public String log() {
        return "Front Left POS" + frontLeft.getCurrentPosition();
    }

    @Override
    public void test() throws InterruptedException {
        throw Master.TODO();
    }

    // --- internals ---

    private double resolveTurn() {

        return rx;
    }

    private static double deadband(double v) {
        return Math.abs(v) < DEADBAND ? 0 : v;
    }

    public static double clamp(double v, double max) {
        return Math.max(-max, Math.min(max, v));
    }





}