package org.firstinspires.ftc.teamcode.OPS.test;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp(name = "Servo Test", group = "Tests")
public class ServoTest extends LinearOpMode {
    public static double change = 0.01;
    public static String servoName = "pollenGate";


    @Override
    public void runOpMode() throws InterruptedException {

        TelemetryManager teelelelel = PanelsTelemetry.INSTANCE.getTelemetry();
        Servo servo = hardwareMap.get(Servo.class, servoName);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.dpadRightWasPressed())
            {
                servo.setPosition(servo.getPosition() + change);
            }
            if (gamepad1.dpadLeftWasPressed())
            {
                servo.setPosition(servo.getPosition() - change);
            }
            if (gamepad1.dpadUpWasPressed())
            {
                servo.setPosition(1);
            }
            if (gamepad1.dpadDownWasPressed())
            {
                servo.setPosition(0);
            }
            if (gamepad1.a)
            {
                servo.setDirection(Servo.Direction.FORWARD);
            }
            if (gamepad1.b)
            {
                servo.setDirection(Servo.Direction.REVERSE);
            }
            telemetry.addData("Pos", servo.getPosition());
            telemetry.addData("Dir", servo.getDirection());

            teelelelel.update(telemetry);


        }
    }
}