package org.firstinspires.ftc.teamcode.control;

import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.functionality.DefaultUpdate;
import org.firstinspires.ftc.teamcode.control.functionality.UtilityObject;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Master;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Squinting;
import org.firstinspires.ftc.teamcode.subsystems.ShillapoforWebcam.Webcam;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.jetbrains.annotations.NotNull;

public class ShootyBrain implements UtilityObject, DefaultUpdate {



    @Override
    public String log() {

        return String.format("Nectar Velocity: %s;\nPollen Velocity:" +
                " %s;\n%s",
                this.mortar.getVelNectar(),
                this.mortar.getVelPollen(),
                this.state.toString());
    }

    @Override
    public void test() throws InterruptedException {

    }

    public enum ShootingState {
        LAUNCHING,
        IDLE,
        FIRING_UP_AND_AIMING,
        OFF,
        THINKING,

        SHUTOFF,
        CLOSING,
        OPENING;


    }

    //a pos that a shot cannot be made from
    boolean poisonedState = false;

    Double overloadPollen = null;
    Double overloadNectar = null;

    ShootingState state;


    final Shooter mortar;
    Gate gates;



    double velPollen =0;
    double velNectar =0;

    Master.TeamColor teamColor;


    Pose lastRecalibratedPose = new Pose(0,0);
    final Master master;
    final Intake intake;

    public ShootyBrain(Master master){
        state = ShootingState.OFF;
        this.mortar = new Shooter(master);
        this.teamColor = Master.getTeam();
        this.gates = new Gate(master);
        this.master = master;
        squinty = master.getSquinter();
        intake= master.getIntake();
    }

    public final Squinting squinty;

    public ShootyBrain(Shooter mort, Master master){
        state = ShootingState.OFF;
        this.mortar = mort;
        this.teamColor = Master.getTeam();
        this.master = master;

        this.gates = new Gate(master);

        squinty = master.getSquinter();
        intake= master.getIntake();

    }

    public void activate(){
        switch (this.state){
            case OFF:
            case IDLE:
                this.state = ShootingState.THINKING;
                break;
            default:
                break;
        }
    }
    public void deactivate(){
        switch (this.state){
            case OFF:
            case IDLE:
            case SHUTOFF:
            case CLOSING:
                return;
            default:
                this.state = ShootingState.SHUTOFF;
                break;
        }
    }

    public Shooter getMortar(){return mortar;}

    public void off(){
        this.state =ShootingState.OFF;
    }

    boolean forceShoot = false;

    public void forceShoot(){forceShoot=true;}
    public void unForceShoot(){forceShoot=false;}

    Timer gateTimer = new Timer();
    
    /**
     * <pre>My thinking with this function is that all the processes of shooting the balls can be handled in an orderly
     * fashion where everything happens in order when we want it to.
     * Preposed process:
     *  Idle/Off -> Shooting button pressed -> Thinking -> calc pos, shooting angle, power, whether shot
     *  can be made, etc. -> Firing up -> getting motor to speed and making sure recalculation do not need to be
     * made -> if recalculations are needed, then thinking -> Opening -> Shooting -> Closing
     *  </>
     */
    public void update(){
        this.master.opmode.telemetry.addLine("Vels " + velNectar + " " + velPollen + "\n" + this.state);
        switch (this.state){
            case IDLE:
                //set velocity to a minimal power, but enough to save time
                this.velNectar = 100;
                this.velPollen =100;
                break;
            case THINKING:
                //calc distance for target velocity than set state to firing up
                think();
                this.state = ShootingState.FIRING_UP_AND_AIMING;

                break;
            case FIRING_UP_AND_AIMING:
                //get to target velocity, then switch to shooting

                //if (poisonedState){poisonedState = false; state = ShootingState.THINKING; break;}

                if ((Math.abs(mortar.getVelPollen() - velPollen) <= 100 && Math.abs(mortar.getVelNectar() - velNectar) <= 100 && correctAngle()) || forceShoot){

                    this.state =ShootingState.OPENING;
                    this.gates.setAllPosition("close");
                    this.gateTimer.reset();

                }
                if(checkForRecalibration()) {
                    this.state = ShootingState.THINKING;
                    break;
                }
                //squinty.correctChuddedAim();

                break;
            case OPENING:
                //make sure nothing else happening while opening
                //when not opened, break;
                //when opened
                if (gateTimer.seconds() >= .5)
                    this.state = ShootingState.LAUNCHING;
                else this.gates.setAllPosition("open");
                break;
            case LAUNCHING:
                //maintain velocity and keep gate open, maybe keep check of how many balls?
                checkForRecalibration();
                this.intake.setAllPower(Intake.intakeSpeed);
                //this.squinty.correctChuddedAim();
                break;

            case SHUTOFF:

                this.state = ShootingState.CLOSING;
                this.gateTimer.reset();

            break;

            case CLOSING:
                //make sure nothing else is happening while gate is closing
                //if(gateTimer.seconds() >= .5) this.state = ShootingState.IDLE;
                gates.setAllPosition("close");
                this.state = ShootingState.IDLE;
                break;

            case OFF:
                this.velPollen =0;
                this.velNectar =0;
                break;
        }
        mortar.setPollenVel(velPollen);
        mortar.setNectarVel(velNectar);

        this.gates.update();
        this.mortar.update();
        this.intake.update();


    }


    public Gate getGate(){return this.gates;}


    private void think() {
        //targ = master.getTarget().closestTarget(teamColor, master.getPose());
        double dist = master.getPose().distance(new Pose(0,0));
        velPollen = overloadPollen != null ? overloadPollen :  1000;   // not final formula
        velNectar = overloadNectar != null ? overloadNectar : 1000;
        lastRecalibratedPose = master.getPose();
    }

    public void setOverloadPollen(Double pollen){
        this.overloadPollen =pollen;
    }
    public void setOverloadNectar(Double pollen){
        this.overloadNectar =pollen;
    }

    Pose targ =new Pose(0,0);

    private static final double needRecalibration = 4;
    private boolean checkForRecalibration(){
        boolean ret = false;



        if (master.getPose().distance(this.lastRecalibratedPose) >= needRecalibration){
            this.state =ShootingState.THINKING;
            ret= true;
            this.lastRecalibratedPose = master.getPose();
        }

        return ret;


    }


    private static double wrap(double a) { return Math.atan2(Math.sin(a), Math.cos(a)); }

    public boolean correctAngle() {
        Pose p = master.getPose();   // read once
        double wanted = Math.atan2(targ.y() - p.y(), targ.x() - p.x());
        return Math.abs(wrap(wanted - p.heading())) <= Math.toRadians(6);
    }


}
