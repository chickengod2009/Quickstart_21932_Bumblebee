


class TelopRed extends OpMode{


  Util util;

  public void init() {
    util = new Util(this, Util.TeamColor.Red, Auton.Telop);
    util.start();
  }

  public void start(){}

  public void loop(){
    util.update();

  }

  public void stop(){
    util.stop();
  }  


}
