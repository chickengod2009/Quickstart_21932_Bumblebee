package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.LLResultError;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.PipelineIndexError;

public class SauronHandler {


    public enum Mode{
        GOOGLESHOWMETHISGUYSBALLS, //TRACKING
        STOPTOSMELLTHEROSES, //FINDING FLOWERS



    }

    public static class LimeResult{
        BallsResult ballsResult;

        public final boolean isAboutBalls;



        public LimeResult(BallsResult ballsResult){
            this.ballsResult = ballsResult;
            isAboutBalls = true;
        }




    }

    public final TowerOfSauron sauron;

    public Mode mode = Mode.GOOGLESHOWMETHISGUYSBALLS;


    public SauronHandler(TowerOfSauron tower){

        sauron= tower;
    }

    public void switchMode(Mode mode){
        this.mode = mode;
    }

    public LimeResult act() throws Exception {
        switch (this.mode){
            case GOOGLESHOWMETHISGUYSBALLS:
                return new LimeResult(produceBalls());
            case STOPTOSMELLTHEROSES:
                break;
        }


        return null;
    }

    BallsResult produceBalls() throws LLResultError, PipelineIndexError {

        switch (this.sauron.getPipeline()){
            case NEURALBALLFINDING:
                return this.sauron.getDetectorBallFinding();
            case SPECIALBALLFINDING:
                return this.sauron.getPythonBallFinding();
            default:
                throw new PipelineIndexError("Not proper pipeline");
        }

    }


}
