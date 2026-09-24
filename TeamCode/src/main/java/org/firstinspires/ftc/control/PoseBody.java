package org.firstinspires.ftc.control;

import com.pedropathing.math.Pose;
import org.jetbrains.annotations.NotNull;

public class PoseBody {
    public final Pose[] body;

    /**
     * @param body Arrange points starting in top left corner, going clockwise
     */
    public PoseBody(@NotNull Pose[] body) {
        this.body = body.clone();
    }

    public Pose[] getWhole() {
        return this.body;
    }

    public Pose get(int index) {
        return this.body[index];
    }

    
    public boolean lineIntersection(Pose pose, Pose targetPose) {
        int n = this.body.length;
        for (int i = 0; i < n; i++) {
            Pose p1 = this.body[i];
            Pose p2 = this.body[(i + 1) % n];

            if (segmentsIntersect(pose, targetPose, p1, p2)) {
                return true;
            }
        }
        return false;
    }


    public Pose center(){
        Util.TODO();
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
}
// I looked up something better
