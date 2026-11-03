package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TouchSensor {
    private DigitalChannel touchSensor;
    public TouchSensor (Master master) {
        touchSensor = master.get("touchSensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean getTouchSensorState() {
        return touchSensor.getState();
    }
}
