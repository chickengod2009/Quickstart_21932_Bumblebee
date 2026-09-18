public class Util{
  //Collect all the motors and other devices at once, and you dont need to call hardwaremap.get multiple times
  private static HashMap<String, HardwareDevice> devices = null;
  //So you only have to actually call all the get methods once
  private static boolean haveIBeenLookedAt = false;



  public Util(Opmode op, CurrentDevice dev){
    if (op == null) throw new RunTimeException("Need an opmode for Util");
    //All the device naming and getting goes here
    if(!Util.haveIBeenLookedAt){
      Util.haveIbeenLookedAt = true;
    }  

    

  } 
  //An idea for states for the robot
  public enum States{}
  //make motor direction switeches less verbose
  public static <T extends DcSimpleMotor> void reverse(@NonNull T[] motors){
    for (DSimpleMotor motor : motors){
    switch (motor.getDirection()){
      case DcSimpleMotor.Direction.REVERSE:
        motor.setDirection(DcSimpleMotor.Direction.FORWARD);
      case DcSimpleMotor.Direction.FORWARD:
        motor.setDirection(DcSimpleMotor.Direction.REVERSE);
        
    }   
    }
  
  } 

  public static <T extends DcSimpleMotor> void reverse(T motor){
    Util.reverse({motor});
  }

  

  //generic get function for all devices
  @SuppressWarnings("unchecked") 
  public <T extends HardwareDevice> T get(String s) throws Exception{
    T ret;
    HardwareDevice dev = (devices.get(s));
    if (dev == null) throw new Exception("Device " + s + "does not exist");
    
    try{
      ret = (T)dev;
    }catch(ClassCastException e){
      throw new Exception(e.toString() +"Motor name" + s);
    }  
    return ret;


}
