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
      case DcMotorSimple.Direction.REVERSE:
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        break;
      case DcMotorSimple.Direction.FORWARD:
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        break;
        
    }   
    }
  
  } 

  public static <T extends Servo> void reverseServo(@NonNull T[] servos){
    for (Servo servo : servos){
    switch (servo.getDirection()){
      case Servo.Direction.REVERSE:
        servo.setDirection(Servo.Direction.FORWARD);
        break;
      case Servo.Direction.FORWARD:
        servo.setDirection(Servo.Direction.REVERSE);
        break;
        
    }   
    }
  
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
  public <T extends HardwareDevice> T get(String s){
    T ret;
    HardwareDevice dev = (devices.get(s));
    if (dev == null) throw new RuntimeException("Device " + s + "does not exist");
    
    try{
      ret = (T)dev;
    }catch(ClassCastException e){
      throw new IllegalArgumentException(e.toString() +"Motor name:" + s);
    }  
    return ret;


}
