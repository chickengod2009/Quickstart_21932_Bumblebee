package org.firstinspires.ftc.control;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Mortar;
import org.firstinspires.ftc.teamcode.subsystems.Util;

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
        REDSEESAW,
        BLUESEESAW,
        FLOWERS,
    }    
    //a pos that a shot cannot be made from
    boolean poisonedState = false;

    ShootingState state;
    Target target;

    Mortar mortar;

    float vel =0;

    Util.TeamColor teamColor;

    Pose previousPose = new Pose(0,0);
    
    public MortarBrain(Util util){
        state = ShootingState.OFF;
        this.mortar = new Mortar(util);
        this.teamColor = util.getTeam();
    }


    public MortarBrain(Mortar mort, Util util){
        state = ShootingState.OFF;
        this.mortar = mort;
        this.teamColor = util.getTeam();

    }

    public void activate(/*Maybe ad poise*/){
        switch (this.state){
            case OFF:
            case IDLE:
                this.state = ShootingState.FIRING_UP;
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
                this.state = ShootingState.IDLE;
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

                break;
            case FIRING_UP:
                //get to target velocity, then switch to shooting

                if (poisonedState){poisonedState = false; state = ShootingState.THINKING;}
                checkForRecalibration(pose);
                break;
            case OPENING:
                //make sure nothing else happening while opening
                break;
            case SHOOTING:
                //maintain velocity and open gate or whatever to start opening
                checkForRecalibration(pose);
                break;

            case CLOSING:
                //make sure nothing else is happening while gate is closing
                break;

            case OFF:
                this.mortar.setPower(0);
        }
    }

    private void think(Pose pose){
        //logic
        this.vel = 0; //set vel here
        this.previousPose = pose;
    }

    private void checkForRecalibration(Pose pose){
        // if distance to large, reset to thinking
        throw new RuntimeException("TODO!");
    }


}
