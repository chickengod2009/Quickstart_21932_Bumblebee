package org.firstinspires.ftc.teamcode.control.field;

import com.pedropathing.math.Pose;

public interface FieldShape {
    /** True if the segment from..to touches this shape. */
    boolean lineIntersection(Pose from, Pose to);

    Pose center();
}