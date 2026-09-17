public class Util{
  //Collect all the motors and other devices at once, and you dont need to call hardwaremap.get multiple times
  private static HashMap<String, HardwareDevice> devices = null;
  //So you only have to actually call all the get methods once
  private static boolean haveIBeenLookedAt = false;



  public Util(Opmode op){
    //All the device naming and getting goes here
    if(!Util.haveIBeenLookedAt){
      Util.haveIbeenLookedAt = true;
    }  

  } 
  //An idea for states for the robot
  public enum States{}
  @SuppressWarnings("unchecked") 
  public <T extends HardwareDevice> T get(String s) throws Exception{
    T ret;
    HardwareDevice dev = (devices.get(s));
    if (s==null) throw new Exception(":(")
    try{
      ret = (T)dev;
    }catch(ClassCastException e){
      throw new Exception(e.toString() +"Motor name" + s);
    }  
    return ret;


}
