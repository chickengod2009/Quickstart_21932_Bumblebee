package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

/**
 *
 *
 * This is designed to both get rid of boilerplate code while allowing for easy
 * access to the devices.
 * Due to how FTC control hub works, this class, whenever initiated, must be closed as,
 * if not, the motor ptrs will point to invalid memory.
 * <pre>{@code
 * public Util ut = new Util(Opmode);
 *  //opmode end function
 * public void stop(){
 *     ut.close();
 * }
 *
 * //you could also use a try with resources to auto close
 *
 * try (Util ut = new Util(opmode)){
 *     //program
 * }catch (Exception e){
 *     //other stuff
 * }
 * //auto closes here
 *
 *
 * // using get function
 * Util util = new Util(opmode);
 * DcMotorSimple = util.get("motor_name"); // Auto converts to DcmotorSimple if you gave right name
 * util.close();
 *
 *
 * }
 *
 *
 * </>
 * **/
public class Util implements AutoCloseable{
  //Collect all the motors and other devices at once, and you dont need to call hardwaremap.get multiple times
  private HashMap<String, HardwareDevice> devices = null;
  //So you only have to actually call all the get methods once
  private static boolean haveIBeenLookedAt = false;



  public Util(OpMode op){
    if (op == null) throw new RuntimeException("Need an opmode for Util");
    //All the device naming and getting goes here
    if(!Util.haveIBeenLookedAt){
      Util.haveIBeenLookedAt = true;
      this.open(op);
    }  
    devices = new HashMap<>();

    

  } 
  @Override
  public void close(){

    if(devices != null) devices.clear();
    haveIBeenLookedAt = false;
    
  }  
  //An idea for states for the robot
  public enum States{}
  //make motor direction switeches less verbose
  public static <T extends DcMotorSimple> void reverseMotor(@NonNull T[] motors){
    for (DcMotorSimple motor : motors){
    switch (motor.getDirection()){
      case REVERSE:
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        break;
      case FORWARD:
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        break;
        
    }   
    }
  
  } 

  public static <T extends Servo> void reverseServo(@NonNull T[] servos){
    for (Servo servo : servos){
    switch (servo.getDirection()){
      case REVERSE:
        servo.setDirection(Servo.Direction.FORWARD);
        break;
      case FORWARD:
        servo.setDirection(Servo.Direction.REVERSE);
        break;
        
    }   
    }
  
  }

  /**
   *
   * @param op
   * This function is used by constructor, but it can be used to reopen a closed
   * util. Note that the map is static, so closing one util closes all, and same for opening!
   * It takes in an opmode
   * and goes through all the hardware, assigning a device to a string in the hash map.
   * <pre>{@code
   *
   * Util util = new Util(op);
   * //code
   * util.close();
   * //more code and now we need util again
   * util.open();
   *
   * }</>
   */
  public void open(OpMode op){
    //put hardware get functions here
  }


  

  public static <T extends DcMotorSimple> void reverseMotor(@NonNull T motor){
    Util.reverseMotor(new DcMotorSimple[]{motor});
  }


  public static <T extends HardwareDevice> void reverse(@NonNull T[] device){
    byte i =0;
    for(T dev : device){
      if(dev instanceof DcMotorSimple){
        Util.reverseMotor((DcMotorSimple)device[i]);
        i++;
        
      } else if(dev instanceof Servo){
        // servo func
      }  else{ /*maybe just do nothing or maybe throw error*/}
        
        
    }  
    

  }

  

  //generic get function for all devices
  @SuppressWarnings("unchecked") 
  public <T extends HardwareDevice> T get(String s) {
    if(!haveIBeenLookedAt) throw new RuntimeException("Utility has been closed! OpMode is no longer available, must reopen.");
    T ret;
    HardwareDevice dev = (devices.get(s));
    if (dev == null) throw new RuntimeException("Device " + s + "does not exist");

    try {
      ret = (T) dev;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException(e.toString() + "Motor name:" + s);
    }
    return ret;

  }
}
