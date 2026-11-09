package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import com.pedropathing.api.Paths;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Master;

import java.util.ArrayList;

import java.util.List;

import java.util.function.ToDoubleFunction;

public class BallSniffer {


    Master master;
    public BallSniffer(Master master)
    {this.master =master;}

    List<BallStrength> positions;

    BallsResult.BallDetails closest;


    void createPoses(BallsResult results){

        Pose roboPose = master.getPose();

        closest = results.closestBall;

        final Master.TeamColor color = Master.getTeam();

        positions = new ArrayList<>(results.ballCount);

        for (BallsResult.BallDetails details : results.balls){
            if(!details.color.equalTeamColor(color)){
                continue;
            }
            BallStrength ball = new BallStrength(details.ballVectorCart.toPose(), details.color, details.getClusterSize());
            ball.adjustStrength(roboPose);
            positions.add(ball);

            positions.sort(
                (BallStrength ballStrength, BallStrength t1) -> {
                        if(ballStrength.strength > t1.strength) return -1;
                        if(ballStrength.strength == t1.strength) return 0;
                        if(ballStrength.strength < t1.strength) return 1;
                        return 0;
                }
            );

        }



    }

    public static final Pose
            RedCornerBlueGoal = new Pose(0,0),
            RedCornerRedGoal = new Pose(0,0),
            BlueCornerBlueGoal = new Pose(0,0),
            BlueCornerRedGoal = new Pose(0,0);


    public ArrayList<Path> formulatePaths(BallsResult res){
        this.createPoses(res);


        List<BallStrength> topPos = positions.subList(0, positions.size()/2);

        ArrayList<Path> paths = new ArrayList<>(positions.size()/2);











        return paths;
    }


    public static class BallStrength{
        public final Pose pos;
        public final ColorSensors.Color color;

        double strength =0;

        int neighbors =0;


        ToDoubleFunction<Pose> func;

        public BallStrength(Pose pos, ColorSensors.Color color, int n, ToDoubleFunction<Pose> func){
            this.pos = pos;
            this.color =color;
            neighbors = n;

            this.func =func;



        }
        public BallStrength(Pose pos, ColorSensors.Color color, int n){
            this.pos = pos;
            this.color =color;
            neighbors = n;

            func =
                    (Pose pose)  -> {
                        double ret =0;

                        ret += (this.color == ColorSensors.Color.YELLOW) ? 1: 2;
                        double dist = this.pos.distance(pose);
                        ret -= dist/100.0;

                        ret += ((double)neighbors)/2.0;


                        return ret;

                    }
            ;



        }

        public void adjustStrength(Pose roboPose){
            strength = func.applyAsDouble(roboPose);
        }

    }

    
}
