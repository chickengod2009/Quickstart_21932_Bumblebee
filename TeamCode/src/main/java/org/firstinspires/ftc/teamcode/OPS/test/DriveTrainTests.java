



public class DriveTrainTest extends Opmode{

  Util util;


  @Override
  public void start(){

    this.telementry.addLine("Select opmode: \n X: <>, Y: <> "/*ETC*/);

    OpModeFunctions fun = null;

    do{

      //check what button was pressed
    

    }while(true);  

    util = new Util(this, Util.RED, fun);

    util.start();
  }


  @Override
  punlic void loop(){
    util.update();
  } 

  @Override
  punlic void stop(){
    util.fin();
  } 






}
