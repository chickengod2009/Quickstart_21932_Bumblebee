package org.firstinspires.ftc.control;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Util;
import org.jetbrains.annotations.NotNull;

public class MortarBrain {

    public enum ShootingState {
        SHOOTING,
        IDLE,
        FIRING_UP,
        OFF,
        THINKING,
        CLOSING,
        OPENING,
    }
    public enum Target{
        SEESAW,
        FLOWERS;



        private Pose closestRedSeesaw(Pose pose){

            throw new RuntimeException("TODO!!");
            
        }    

        private Pose closestBlueSeesaw(Pose pose){

            throw new RuntimeException("TODO!!");
            
        }    

        public Pose closestSeesaw(Util.TeamColor color, Pose pose){

            switch(color){
                case RED:
                    return closestRedSeesaw(pose);
                case BLUE:
                    return closestBlueSeesaw(pose);
            }    

        }    

        public Pose closestFlower(Pose pose){


        }    

        public Pose closestTarget(Util.TeamColor color, Pose pose){
            switch (this){
                case SEESAW:
                    return closestSeesaw(color, pose);
                    break;
                case FLOWERS:
                    return closestFlower(pose);
            }        
                    
        }
        // final PoseBody body;

        // Target(PoseBody bod){
        //     this.body = bod;
        // }
    }
    //a pos that a shot cannot be made from
    boolean poisonedState = false;

    ShootingState state;
    Target target = Target.SEESAW;

    Mortar mortar;

    double vel =0;

    Util.TeamColor teamColor;


    Pose lastRecalibratedPose = new Pose(0,0);
    
    public MortarBrain(Util util){
        state = ShootingState.OFF;
        this.mortar = new Mortar(util);
        this.teamColor = Util.getTeam();
    }


    public MortarBrain(Mortar mort, Util util){
        state = ShootingState.OFF;
        this.mortar = mort;
        this.teamColor = Util.getTeam();

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
            case CLOSING:    
                break;
            default:
                this.state = ShootingState.CLOSING;
                break;
        }
    }

    public void off(){
        this.state =ShootingState.OFF;
    }


    
    /**
     * <pre>My thinking with this function is that all the processes of shooting the balls can be handled in an orderly
     * fashion where everything happens in order when we want it to.
     * Preposed process:
     *  Idle/Off -> Shooting button pressed -> Thinking -> calc pos, shooting angle, power, whether shot
     *  can be made, etc. -> Firing up -> getting motor to speed and making sure recalculation do not need to be
     * made -> if recalculations are needed, then thinking -> Opening -> Shooting -> Closing
     *  </>
     */
    public void update(Pose pose){
        switch (this.state){
            case IDLE:
                //set velocity to a minimal power, but enough to save time
                this.mortar.setVelocity(100);
                break;
            case THINKING:
                //calc distance for target velocity than set state to firing up
                think(pose);
                this.state = ShootingState.FIRING_UP;
                break;
            case FIRING_UP:
                //get to target velocity, then switch to shooting
                mortar.setVelocity(vel);
                if (poisonedState){poisonedState = false; state = ShootingState.THINKING; break;}
                if(checkForRecalibration(pose)) break;
                if (Math.abs(mortar.getVelocity() - vel) <= 100) this.state =ShootingState.OPENING;

                break;
            case OPENING:
                //make sure nothing else happening while opening
                //when not opened, break;
                //when opened
                this.state = ShootingState.SHOOTING;
                break;
            case SHOOTING:
                //maintain velocity and keep gate open, maybe keep check of how many balls?
                checkForRecalibration(pose);
                break;

            case CLOSING:
                //make sure nothing else is happening while gate is closing
                this.state = ShootingState.IDLE;
                break;

            case OFF:
                this.mortar.setPower(0);
        }


    }

    public void setTarg(Target targ){
        this.target = targ;
    }   

    private void think(@NotNull Pose pose){
        //logic



        

        

        
        Pose targ = target.closestTarget(teamColor, pose);
                
        // if pose block by body :- poisdenedState = true;
        double dist = pose.distance(targ);
        this.vel = dist*100;//Not final formula!!
        

    }

    private static final byte needRecalibration = 1;
    private boolean checkForRecalibration(Pose pose){
        boolean ret = false;
        if (pose.distance(this.lastRecalibratedPose) >= needRecalibration){
            this.state =ShootingState.THINKING;
            ret= true;
            this.lastRecalibratedPose = pose;
        }

        return ret;


    }


}
