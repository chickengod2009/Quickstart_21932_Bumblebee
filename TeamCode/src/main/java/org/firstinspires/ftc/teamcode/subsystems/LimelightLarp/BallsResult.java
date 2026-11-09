package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import com.pedropathing.math.Vector2D;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.jetbrains.annotations.NotNull;

public class BallsResult {
    //How many balls, the distance of each + direction (Vector 2ds), Closest Balls, Any Clusters,

    public final int ballCount;

    public final BallDetails[] balls;

    public final BallDetails closestBall;

    public BallsResult(@NotNull double[] x, double[] y, ColorSensors.RGB[] colors){
        if (x.length != y.length || x.length!=colors.length) throw Master.TODO();
        BallDetails min= new BallDetails(999,999, new ColorSensors.RGB(0,0,0));
        BallDetails[] bally = new BallDetails[x.length];
        for (int i=0; i<x.length; i++){
            bally[i] = new BallDetails(x[i],y[i],colors[i]);

            if(min.ballVectorCart.magnitude() >= bally[i].ballVectorCart.magnitude()){
                min = bally[i];
            }

        }
        ballCount = bally.length;
        balls = bally;
        closestBall = min;
        setClusters();
    }
    public BallsResult(@NotNull double[] x, double[] y, ColorSensors.Color[] colors){
        if (x.length != y.length || x.length!=colors.length) throw Master.TODO();
        BallDetails min= new BallDetails(999,999, new ColorSensors.RGB(0,0,0));
        BallDetails[] bally = new BallDetails[x.length];
        for (int i=0; i<x.length; i++){
            bally[i] = new BallDetails(x[i],y[i],colors[i]);

            if(min.ballVectorCart.magnitude() >= bally[i].ballVectorCart.magnitude()){
                min = bally[i];
            }

        }
        ballCount = bally.length;
        balls = bally;
        closestBall = min;
    }

    public static final double CLUSTER_RANGE = .3;

    public int getBallCount() {return this.ballCount;}

    public void setClusters(){

        for(BallDetails ball : balls){
            int cluster = 0;
            for (BallDetails ball2 : balls){
                if (ball == ball2) continue;
                double ang = ball.ballVectorCart.angleTo(ball2.ballVectorCart);
                double distOne = ball.ballVectorCart.magnitude();
                double distTwo = ball2.ballVectorCart.magnitude();
                double sq = distOne*distOne + distTwo*distTwo - 2*distOne*distTwo*Math.cos(ang);
                double finalDistance = Math.sqrt(Math.max(0, sq));
                if ((finalDistance) <= CLUSTER_RANGE){
                    cluster++;
                }

            }
            ball.setPartOfCluster(cluster);
        }

    }


    public static class BallDetails{
        final Vector2D ballVectorCart;

        final Vector2D ballVectorPolar;
        ColorSensors.Color color;

        private int clusterSize = 0;

        public BallDetails(double x, double y, ColorSensors.RGB rgb){
            this.ballVectorCart = Vector2D.cartesian(x,y);
            this.ballVectorPolar = Vector2D.polar(ballVectorCart.magnitude(), ballVectorCart.theta());
            this.color = rgb.assignColor(200);

        }
        public BallDetails(double x, double y, ColorSensors.Color rgb){
            this.ballVectorCart = Vector2D.cartesian(x,y);
            this.ballVectorPolar = Vector2D.polar(ballVectorCart.magnitude(), ballVectorCart.theta());
            this.color = rgb;

        }



        public void setPartOfCluster(int size){this.clusterSize=size;}


        public void turnIntoPose(){
            double x = this.ballVectorCart.x();
            double y = this.ballVectorCart.y();
        }

        public int getClusterSize() { return clusterSize; }


    }





}
