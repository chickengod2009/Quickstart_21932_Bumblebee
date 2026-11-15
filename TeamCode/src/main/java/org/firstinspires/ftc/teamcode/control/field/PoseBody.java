package org.firstinspires.ftc.teamcode.control.field;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PoseBody implements FieldShape{
    public final ArrayList<Pose> body;

    /**
     * @param body Arrange points starting in top left corner, going clockwise
     */

    public PoseBody() {
        this.body = new ArrayList<>();
    }

    public void add(Pose pose){
        this.body.add(pose);
    }

    public ArrayList<Pose> getWhole() {
        return this.body;
    }

    public Pose get(int index) {
        return this.body.get(index);
    }





    private static boolean segmentsIntersect(Pose a, Pose b, Pose c, Pose d) {
        int uno = orientation(a, b, c);
        int dous = orientation(a, b, d);
        int tres = orientation(c, d, a);
        int quad = orientation(c, d, b);


        if (uno != dous && tres != quad) {
            return true;
        }


        if (uno == 0 && onSegment(a, c, b)) return true;
        if (dous == 0 && onSegment(a, d, b)) return true;
        if (tres == 0 && onSegment(c, a, d)) return true;
        return quad == 0 && onSegment(c, b, d);
    }


    private static int orientation(Pose p, Pose q, Pose r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) - (q.x() - p.x()) * (r.y() - q.y());
        if (Math.abs(val) < 1e-9) return 0;
        return (val > 0) ? 1 : 2;
    }


    private static boolean onSegment(Pose p, Pose q, Pose r) {
        return q.x() <= Math.max(p.x(), r.x()) && q.x() >= Math.min(p.x(), r.x()) &&
                q.y() <= Math.max(p.y(), r.y()) && q.y() >= Math.min(p.y(), r.y());
    }



        public PoseBody(@NotNull ArrayList<Pose> body) {
            this.body = new ArrayList<>(body);   // instead of the unchecked clone cast
        }

        @Override
        public boolean lineIntersection(Pose pose, Pose targetPose) {
            int n = body.size();
            for (int i = 0; i < n; i++) {
                if (Geometry.segmentsIntersect(pose, targetPose, body.get(i), body.get((i + 1) % n))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Pose center() {   // vertex average, which also fills in your TODO
            double x = 0, y = 0;
            for (Pose p : body) { x += p.x(); y += p.y(); }
            return new Pose(x / body.size(), y / body.size());
        }
        // delete the private segmentsIntersect / orientation / onSegment


    public boolean contains(Pose p) {
        boolean inside = false;
        int n = body.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = body.get(i).x(), yi = body.get(i).y();
            double xj = body.get(j).x(), yj = body.get(j).y();
            if ((yi > p.y()) != (yj > p.y())
                    && p.x() < (xj - xi) * (p.y() - yi) / (yj - yi) + xi) {
                inside = !inside;
            }
        }
        return inside;
    }

    public boolean intersects(PoseSegments segments) {
        return segments.intersects(this);
    }

}
// I looked up something better
