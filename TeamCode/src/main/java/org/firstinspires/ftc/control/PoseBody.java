package org.firstinspires.ftc.control;

import com.pedropathing.math.Pose;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PoseBody {
    public final Pose[] body;

    /**
     *
     * @param body, Arrange points by starting in top left corner, the going clockwise
     */
    public PoseBody(@NotNull Pose[] body){
        this.body = body.clone();
    }

    public Pose[] getWhole(){
        return this.body;
    }

    public Pose get(int index){
        return this.body[index];
    }

    private static class Line{
        public final Pose a;
        public final Pose b;
        double k;
        double m;

        public Line(@NotNull Pose a, @NotNull Pose b){
            this.a = Math.min(a.x(),b.x()) == a.x() ? a : b;
            this.b = Math.max(b.x(),a.x()) == b.x() ? b : a;
            this.m =
                    (this.a.y()- this.b.y())
                    /
                    (this.a.x()-this.b.x());
            this.k =
                    this.a.y() -this.a.x()*this.m;
        }

        public @Nullable Optional<Pose> intersects(Line other){
            if (this.m == other.m) {
                if (this.k != other.k) return Optional.empty();
                else return null;
            }

            double x =
                    ((other.a.x()*other.m) + other.k - (this.k))
                    /
                    this.m;
            double y = this.m*x + this.k;
            return Optional.of(new Pose(x,y));
        }
    }

    //checks that the line made by pose and targetPose intersects with the body
    public boolean lineIntersection(Pose pose, Pose targetPose){
        int i=0;
        Line targSeg = new Line(pose, targetPose);
       for (Pose point : this.body){
           Line bodySeg = new Line(point, this.body[(i+1)%this.body.length]);
           @Nullable Optional<Pose> intersect = bodySeg.intersects(targSeg);
           if (intersect == null || intersect.isEmpty()) continue;
           else {
               Pose extract = intersect.get();

               boolean inBound =
                       (bodySeg.a.x() <= extract.x() && extract.x() <= bodySeg.b.x())
                       &&
                        (Math.min(bodySeg.a.y(), bodySeg.b.y()) <= extract.x() && extract.x() <= Math.max(bodySeg.a.y(), bodySeg.b.y()));
               if(inBound)
                   return  true;



           }
           ++i;

       }
       return false;
    }




}
