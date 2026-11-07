package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import com.qualcomm.hardware.limelightvision.LLResult;

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

    BallsResult produceBalls() throws Exception{
        if (this.sauron.getPipeline() != PipelineIndex.SPECIALBALLFINDING) {
            throw new Exception("Not in the right pipeLine");
        }

        return null;
    }


}
