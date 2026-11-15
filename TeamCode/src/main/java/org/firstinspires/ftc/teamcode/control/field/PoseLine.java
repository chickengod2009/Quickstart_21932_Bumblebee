package org.firstinspires.ftc.teamcode.control.field;

import com.pedropathing.math.Pose;

public class PoseLine implements FieldShape {
    public final Pose start;
    public final Pose end;

    public PoseLine(Pose start, Pose end) {
        this.start = start;
        this.end = end;
    }

    public Pose getStart() { return start; }
    public Pose getEnd()   { return end; }

    @Override
    public boolean lineIntersection(Pose from, Pose to) {
        return Geometry.segmentsIntersect(from, to, start, end);
    }



    @Override
    public Pose center() {
        return new Pose((start.x() + end.x()) / 2.0,
                (start.y() + end.y()) / 2.0);
    }

    public double length() {
        return start.distance(end);
    }
}