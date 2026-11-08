package org.firstinspires.ftc.teamcode.subsystems.LimelightLarp;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.LLResultError;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.NullPythonResult;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.NullResult;
import org.firstinspires.ftc.teamcode.subsystems.LimelightLarp.llError.PipelineIndexError;
import org.firstinspires.ftc.teamcode.subsystems.Master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerOfSauron {


    private static final long MAX_STALE_MS = 1;
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
        this.theOneRing.pipelineSwitch(pipeline.index);
    }
    public PipelineIndex getPipeline(){
        return this.pipeline;
    }

    public LLResult getLatestResult(){
        return this.theOneRing.getLatestResult();
    }

    public static final double hBall = .5;
    public static final double hCam =.5;

    public static final double mountAngleDegree = 33;
    public BallsResult getPythonBallFinding() throws LLResultError {

        double[] py = getPy();

        int n = (int) py[0];
        n = Math.max(0, Math.min(n, (py.length - 1) / 3));      // never read past the array

        double[] x = new double[n], y = new double[n];
        ColorSensors.Color[] colors = new ColorSensors.Color[n];
        ColorSensors.Color[] byId = {ColorSensors.Color.RED, ColorSensors.Color.BLUE, ColorSensors.Color.YELLOW};

        double pitch = Math.toRadians(-mountAngleDegree);       // positive = camera tilted down
        double dz = hBall - hCam;                               // negative: ball center is below the lens
        int k = 0;

        for (int i = 0; i < n; i++) {

            int id = (int) py[3 + 3 * i];
            if (id < 0 || id >= byId.length) continue;

            // Ray from the camera, in world axes (forward, up), scaled so lateral = tanTx
            double[] xy = projectToFloor(py[1 + 3 * i], py[2 + 3 * i]);
            if (xy ==null) continue;
            // scale to reach the ball's height
            x[k] = xy[0];                                            // forward
            y[k] = xy[1];                                        // right-positive; negate for left-positive
            colors[k] = byId[id];
            k++;

        }

        return new BallsResult(Arrays.copyOf(x, k), Arrays.copyOf(y, k), Arrays.copyOf(colors, k));
    }

    @NonNull
    private double[] getPy() throws NullResult, PipelineIndexError {
        LLResult res = theOneRing.getLatestResult();
        if (res == null) throw new NullResult("No result");
        if (res.getPipelineIndex() != PipelineIndex.SPECIALBALLFINDING.index)      // the index SPECIALBALLFINDING maps to
            throw new PipelineIndexError("Result is from pipeline " + res.getPipelineIndex());
        if (res.getStaleness() > MAX_STALE_MS)
            throw new NullResult("Stale result: " + res.getStaleness() + " ms");

        double[] py = res.getPythonOutput();
        if (py == null || py.length < 1) throw new NullPythonResult("No python result");
        return py;
    }




    private double[] projectToFloor(double txDeg, double tyDeg) {
        double pitch = Math.toRadians(-mountAngleDegree);
        double tanTx = Math.tan(Math.toRadians(txDeg));
        double tanTy = Math.tan(Math.toRadians(tyDeg));
        double a = Math.cos(pitch) + tanTy * Math.sin(pitch);
        double b = -Math.sin(pitch) + tanTy * Math.cos(pitch);
        if (b >= -1e-6) return null;                 // at or above the horizon
        double t = (hBall - hCam) / b;
        return new double[]{ t * a, t * tanTx };     // {forward, lateral}
    }

    private static final double MIN_CONFIDENCE = 0.4;   // tune

    public BallsResult getDetectorBallFinding() throws LLResultError {
        LLResult res = theOneRing.getLatestResult();
        if (res == null) throw new NullResult("No result");
        if (res.getPipelineIndex() != PipelineIndex.NEURALBALLFINDING.index)
            throw new PipelineIndexError("Result is from pipeline " + res.getPipelineIndex());
        if (res.getStaleness() > MAX_STALE_MS)
            throw new NullResult("Stale result: " + res.getStaleness() + " ms");

        List<LLResultTypes.DetectorResult> dets = res.getDetectorResults();
        List<double[]> pos = new ArrayList<>();
        List<ColorSensors.Color> cols = new ArrayList<>();

        if (dets != null) {
            for (LLResultTypes.DetectorResult d : dets) {
                if (d.getConfidence() < MIN_CONFIDENCE) continue;
                ColorSensors.Color c = colorFor(d.getClassName());
                if (c == null) continue;
                double[] p = projectToFloor(d.getTargetXDegrees(), d.getTargetYDegrees());
                if (p == null) continue;
                pos.add(p);
                cols.add(c);
            }
        }

        double[] x = new double[pos.size()], y = new double[pos.size()];
        for (int i = 0; i < pos.size(); i++) { x[i] = pos.get(i)[0]; y[i] = pos.get(i)[1]; }
        return new BallsResult(x, y, cols.toArray(new ColorSensors.Color[0]));
    }

    private static ColorSensors.Color colorFor(String cls) {
        String s = cls.toLowerCase();
        if (s.startsWith("yellow")) return ColorSensors.Color.YELLOW;
        if (s.startsWith("red"))    return ColorSensors.Color.RED;
        if (s.startsWith("blue"))   return ColorSensors.Color.BLUE;
        return null;
    }


}
