package org.firstinspires.ftc.teamcode.control.field;



import com.pedropathing.math.Pose;

public final class Geometry {
    private Geometry() {}

    static boolean segmentsIntersect(Pose a, Pose b, Pose c, Pose d) {
        int o1 = orientation(a, b, c);
        int o2 = orientation(a, b, d);
        int o3 = orientation(c, d, a);
        int o4 = orientation(c, d, b);

        if (o1 != o2 && o3 != o4) return true;

        if (o1 == 0 && onSegment(a, c, b)) return true;
        if (o2 == 0 && onSegment(a, d, b)) return true;
        if (o3 == 0 && onSegment(c, a, d)) return true;
        return o4 == 0 && onSegment(c, b, d);
    }

    static int orientation(Pose p, Pose q, Pose r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) - (q.x() - p.x()) * (r.y() - q.y());
        if (Math.abs(val) < 1e-9) return 0;
        return (val > 0) ? 1 : 2;
    }

    static boolean onSegment(Pose p, Pose q, Pose r) {
        return q.x() <= Math.max(p.x(), r.x()) && q.x() >= Math.min(p.x(), r.x())
                && q.y() <= Math.max(p.y(), r.y()) && q.y() >= Math.min(p.y(), r.y());
    }


    static double pointSegmentDistance(Pose p, Pose a, Pose b) {
        double dx = b.x() - a.x(), dy = b.y() - a.y();
        double lenSq = dx * dx + dy * dy;
        double t = lenSq < 1e-12 ? 0 : ((p.x() - a.x()) * dx + (p.y() - a.y()) * dy) / lenSq;
        t = Math.max(0, Math.min(1, t));
        return Math.hypot(p.x() - (a.x() + t * dx), p.y() - (a.y() + t * dy));
    }

    /** Shortest distance between segments a-b and c-d (0 if they touch or cross). */
    static double segmentDistance(Pose a, Pose b, Pose c, Pose d) {
        if (segmentsIntersect(a, b, c, d)) return 0;
        return Math.min(
                Math.min(pointSegmentDistance(a, c, d), pointSegmentDistance(b, c, d)),
                Math.min(pointSegmentDistance(c, a, b), pointSegmentDistance(d, a, b)));
    }
}
