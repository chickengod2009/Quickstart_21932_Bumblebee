package org.firstinspires.ftc.teamcode.OPS.test;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.functionality.OpModeFunctions;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Master;
@Configurable
@TeleOp(name = "A Communist, a conservative, and a liberal")
public class ColorSensorTest extends OpMode {
    Master master;
    ColorSensors CSensors;

    TelemetryManager telemetryManager;
    @Override
    public void init() {
        master = new Master(this, Master.TeamColor.BLUE, OpModeFunctions.TEST_1);
        CSensors = new ColorSensors(master);
        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void loop() {
        CSensors.update();
        telemetryManager.addData("R Value: ", CSensors.getR(ColorSensors.RGB.SensorID.ONE));
        telemetryManager.addData("G Value: ", CSensors.getG(ColorSensors.RGB.SensorID.ONE));
        telemetryManager.addData("B Value: ", CSensors.getB(ColorSensors.RGB.SensorID.ONE));
        telemetryManager.update(telemetry);
    }

}