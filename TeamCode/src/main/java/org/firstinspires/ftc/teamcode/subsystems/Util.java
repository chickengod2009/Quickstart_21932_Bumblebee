public class Util{
  //Collect all the motors and other devices at once, and you dont need to call hardwaremap.get multiple times
  private static HashMap<String, HardwareDevice> devices = null;
  //So you only have to actually call all the get methods once
  private static boolean haveIBeenLookedAt = false;



  public Util(Opmode op, CurrentDevice dev){
    if (op == null) throw new RuntimeException("Need an opmode for Util");
    //All the device naming and getting goes here
    if(!Util.haveIBeenLookedAt){
      Util.haveIBeenLookedAt = true;
    }  

    

  } 
  //An idea for states for the robot
  public enum States{}
  //make motor direction switeches less verbose
  public static <T extends DcSimpleMotor> void reverseMotor(@NonNull T[] motors){
    for (DcSimpleMotor motor : motors){
    switch (motor.getDirection()){
      case DcSimpleMotor.Direction.REVERSE:
        motor.setDirection(DcSimpleMotor.Direction.FORWARD);
        break;
      case DcSimpleMotor.Direction.FORWARD:
        motor.setDirection(DcSimpleMotor.Direction.REVERSE);
        break;
        
    }   
    }
  
  } 


  

  public static <T extends DcSimpleMotor> void reverseMotor(@NonNull T motor){
    Util.reverse(new DcSimpleMotor[]{motor});
  }


  public static <T extends HardwareDevice> void reverse(@NonNull T[] device){
    
    for(T dev : device){
      if(dev instanceof DcSimpleMotor){
        Util.reverseMotor((DcSimple)device);
      }  // if else chain here
        
        
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
      throw new IllegalArgumentException(e.toString() +"Motor name" + s);
    }  
    return ret;


}
