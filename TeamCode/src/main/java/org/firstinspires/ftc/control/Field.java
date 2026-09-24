package org.firstinspires.ftc.control;

import com.pedropathing.math.Pose;

public class Field {


    public static PoseBody seesaws(){

        Pose topLeft = new Pose(46,90);
        Pose topRed = new Pose(58,80);
        Pose topBlue = new Pose(84,80);
        Pose topRight = new Pose(95,90);
        Pose bottomRight = new Pose(95,52);
        Pose bottomBlue = new Pose(84,60);
        Pose bottomRed = new Pose(58,60);
        Pose bottomLeft = new Pose(46,52);

        return new PoseBod(new Pose[]{topLeft,topRed,topBlue,topRight,bottomRight,bottomBlue,bottomRed,bottomLeft});
        
        
        throw  new RuntimeException("Todo!");
    }
    public static Pose redGoalTop(){
        throw new RuntimeException("Todo!!");
    }
    public static Pose redGoalBottom(){
        throw new RuntimeException("Todo!!");
    }
    public static Pose blueGoalTop(){
        throw new RuntimeException("Todo!!");
    }
    public static Pose blueGoalBottom(){
        throw new RuntimeException("Todo!!");
    }

}
