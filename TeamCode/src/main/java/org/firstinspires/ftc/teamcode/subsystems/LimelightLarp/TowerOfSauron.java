package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.subsystems.Master;

public class TowerOfSauron {


    public final Limelight3A theOneRing;

    private boolean started= false;

    private PipelineIndex pipeline;

    private LLResult storedResult;



    public TowerOfSauron(Master master){
        this.theOneRing = master.get("Limelight");

        this.theOneRing.setPollRateHz(100);

    }


    public void start(){
        if(!started) this.theOneRing.start();
        started=true;
    }

    public void setPipeline(PipelineIndex index){
        this.pipeline=index;
    }
    public PipelineIndex getPipeline(){
        return this.pipeline;
    }

    public LLResult getLatestResult(){
        return this.theOneRing.getLatestResult();
    }










}
