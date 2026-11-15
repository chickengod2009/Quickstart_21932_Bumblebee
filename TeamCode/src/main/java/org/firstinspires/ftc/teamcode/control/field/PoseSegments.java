package org.firstinspires.ftc.teamcode.control.field;

import com.pedropathing.math.Pose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** An open chain of line segments: p0 -> p1 -> p2 ... (last point does NOT connect back to first). */
public class PoseSegments implements FieldShape {
    private final ArrayList<Pose> points;

    public PoseSegments() {
        this.points = new ArrayList<>();
    }

    public PoseSegments(List<Pose> points) {
        this.points = new ArrayList<>(points);
    }

    public PoseSegments add(Pose pose) {
        points.add(pose);
        return this;   // chainable: new PoseSegments().add(a).add(b).add(c)
    }

    public List<Pose> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public Pose get(int index) {
        return points.get(index);
    }

    /** Number of line segments, which is points - 1 (0 if fewer than 2 points). */
    public int segmentCount() {
        return Math.max(0, points.size() - 1);
    }

    @Override
    public boolean lineIntersection(Pose from, Pose to) {
        for (int i = 0; i < points.size() - 1; i++) {   // no (i + 1) % n, so it stays open
            if (Geometry.segmentsIntersect(from, to, points.get(i), points.get(i + 1))) {
                return true;
            }
        }
        return false;
    }

    /** Index of the first segment hit, or -1 if none. Handy for knowing which wall you hit. */
    public int firstIntersectingSegment(Pose from, Pose to) {
        for (int i = 0; i < points.size() - 1; i++) {
            if (Geometry.segmentsIntersect(from, to, points.get(i), points.get(i + 1))) {
                return i;
            }
        }
        return -1;
    }

    public double length() {
        double total = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            total += points.get(i).distance(points.get(i + 1));
        }
        return total;
    }

    /** Length-weighted midpoint of the whole chain (the centroid of the curve itself). */
    @Override
    public Pose center() {
        if (points.isEmpty()) throw new IllegalStateException("No points");
        double total = length();
        if (total < 1e-9) return points.get(0);   // single point or all points coincident

        double x = 0, y = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            Pose a = points.get(i), b = points.get(i + 1);
            double len = a.distance(b);
            x += (a.x() + b.x()) / 2.0 * len;
            y += (a.y() + b.y()) / 2.0 * len;
        }
        return new Pose(x / total, y / total);
    }
}