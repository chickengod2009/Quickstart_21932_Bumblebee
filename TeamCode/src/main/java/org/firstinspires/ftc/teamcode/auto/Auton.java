
/**
*An Idea could to be to create a set of Auton Classes and put the coresponding static functions here in the the enum params
 **/ 
public enum Auton{


  Telop(null,null,null),

  AutonBlue1(Auton::todo, Auton::autonBlue1Func, Auton::todo),

  AutonRed1(Auton::todo, Auton::autonRed1Func, Auton::todo);

  public final Consumer<Util> start;
  public final Consumer<Util> update;
  public final Consumer<Util> fin;
  
  private Auton(Consumer<Util> star, Consumer<Util> up, Consumer<Util> finish){
    start =star;
    update = up;
    fin = finish;
                    
  }

  private ArrayList<Pose> poses = null;
  private ArrayList<Path> paths = null;


  private static void autonBlue1Func(Util util){
      throw Util.TODO();
    
  }  

  private static void autonRed1Func(Util util){throw Util.TODO();}

  private static void todo(Util util){throw Util.TODO();}
  


} 
