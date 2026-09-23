package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.jetbrains.annotations.NotNull;

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
 * //Despite being auto closeable, it is best practice to call the clean up ptrs function at the begging of your program incase the previous program interuppted before reaching a close statement
 *
 *
 * }
 *
 *
 * </>
 * **/
public class Util{
  //Collect all the motors and other devices at once, and you don't need to call hardwaremap.get multiple times
  private volatile HashMap<String, HardwareDevice> devices = null;
  //So you only have to actually call all the get methods once




  public Util(OpMode op){
    if (op == null) throw new RuntimeException("Need an opmode for Util");
    //All the device naming and getting goes here
    this.open(op);

    

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

//  /**
//   * This function needs to be called at the beginning of every opmode inorder to fix all the opmode pointers
//   */
//  public static void cleanUpPtrs(){
//    if(Util.devices != null) Util.devices.clear();
//    haveIBeenLookedAt = false;
//  }

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
  private void open(@NotNull OpMode op){
    this.devices = new HashMap<>();

    devices.put("frontLeftMotor",    op.hardwareMap.dcMotor.get("frontLeftMotor"));
    devices.put("backLeftMotor",     op.hardwareMap.dcMotor.get("backLeftMotor"));
    devices.put("frontRightMotor",   op.hardwareMap.dcMotor.get("frontRightMotor"));
    devices.put("backRightMotor",    op.hardwareMap.dcMotor.get("backRightMotor"));
    devices.put("intakeMotor",       op.hardwareMap.dcMotor.get("intake"));
    devices.put("rollersMotor",      op.hardwareMap.dcMotor.get("roller"));
    devices.put("kicker",            op.hardwareMap.servo.get("kicker"));
    devices.put("turretMotor",       op.hardwareMap.dcMotor.get("turret"));
    devices.put("webcam1",           op.hardwareMap.get(WebcamName.class, "camera"));

    devices.put("shooter",           op.hardwareMap.get(DcMotorEx.class, "flyMotor"));
    devices.put("shooterTwo",        op.hardwareMap.get(DcMotorEx.class, "flyMotor2"));
    devices.put("gate",              op.hardwareMap.servo.get("gate"));
    devices.put("turret",            op.hardwareMap.servo.get("turret"));
    devices.put("turret2",           op.hardwareMap.servo.get("turret2"));
    devices.put("hood",              op.hardwareMap.servo.get("hood"));
    devices.put("topSensor",         op.hardwareMap.get(ColorSensor.class, "topSenor"));

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


  /**
   *
   * @param name
   * @return T
   * @param <T>
   *
   * This works as a generic get function that can give any stored device
   *
   * <pre>{@code
   * Util.cleanUpPtrs();
   * Util util =new Util(opmode);
   * Servo servo = util.get("servo name");
   * DcMotorEx motor  = util.get("motor name");
   *
   * }</>
   *
   */
  @SuppressWarnings("unchecked") 
  public <T extends HardwareDevice> T get(String name) {
    //if(!haveIBeenLookedAt) throw new RuntimeException("Utility has been closed! OpMode is no longer available, must reopen.");
    T ret;
    HardwareDevice dev = (devices.get(name));
    if (dev == null) throw new RuntimeException("Device " + name + "does not exist");

    try {
      ret = (T) dev;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException(e.toString() + "Motor name:" + name);
    }
    return ret;

  }
}
