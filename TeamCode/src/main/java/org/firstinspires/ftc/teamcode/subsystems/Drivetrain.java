package org.firstinspires.ftc.teamcode.subsystems;


//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
//import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.control.Loggable;

//import java.util.HashMap;

//@Config
public class Drivetrain implements Loggable {

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    public static double maxLinear = 1, maxRot = 1, slowLin = 1, slowRot = 1, fastLin = 1, fastRot = 1;

    public static double speedMult = 1;

    public final Util util;

    public static boolean test = false;
    public Drivetrain(Util util) {
        this.util = util;

        // frontLeft = hwMap.dcMotor.get(config.get("frontLeftMotor"));
        // backLeft = hwMap.dcMotor.get(config.get("backLeftMotor"));
        // frontRight = hwMap.dcMotor.get(config.get("frontRightMotor"));
        // backRight = hwMap.dcMotor.get(config.get("backRightMotor"));
        /*
        frontLeft = util.get("frontLeftMotor");
        etc.
        
        */
        frontLeft = util.get("frontLeftMotor");
        backLeft = util.get("backLeftMotor");
        frontRight = util.get("frontRightMotor");
        backRight = util.get("backRightMotor");

        Util.reverse(new HardwareDevice[] {frontLeft, backLeft});


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void update(double x, double y, double rx) {
        frontLeft.setPower( ((y + x - rx) * speedMult));
        backLeft.setPower( ((y - x - rx) * speedMult));
        frontRight.setPower( ((y - x + rx) * speedMult));
        backRight.setPower( ((y + x + rx) * speedMult));
    }

    public void parkMode() {
        speedMult = .30;
//        maxLinear = slowLin;
//        maxRot = slowRot;
    }

    public void speedMode() {
        speedMult = 1;
//        maxLinear = fastLin;
//        maxRot = fastRot;
    }

	@Override
	public String log(){
		// do more here
		return 
			"Front Left POS" + frontLeft.getCurrentPosition();
	}


}
