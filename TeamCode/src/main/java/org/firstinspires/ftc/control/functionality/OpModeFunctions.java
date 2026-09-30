package org.firstinspires.ftc.control.functionality;

import org.firstinspires.ftc.teamcode.subsystems.Util;

import java.util.function.Consumer;

/**
*An Idea could to be to create a set of Auton Classes and put the coresponding static functions here in the the enum params
 **/ 
public enum OpModeFunctions {


  Telop(OpModeFunctions::todo,OpModeFunctions::todo,OpModeFunctions::todo),

  AutonBlue1(OpModeFunctions::todo, OpModeFunctions::autonBlue1Func, OpModeFunctions::todo),

  AutonRed1(OpModeFunctions::todo, OpModeFunctions::autonRed1Func, OpModeFunctions::todo),
  TEST1(OpModeFunctions::todo,OpModeFunctions::todo,OpModeFunctions::todo);

  public final Consumer<Util> start;
  public final Consumer<Util> update;
  public final Consumer<Util> fin;
  
  private OpModeFunctions(Consumer<Util> star, Consumer<Util> up, Consumer<Util> finish){
    start =star;
    update = up;
    fin = finish;
                    
  }

//  private ArrayList<Pose> poses = null;
//  private ArrayList<Path> paths = null;


  private static void autonBlue1Func(Util util){
      throw Util.TODO();
    
  }  

  private static void autonRed1Func(Util util){throw Util.TODO();}

  private static void todo(Util util){throw Util.TODO();}


  public boolean isSafe(){
    boolean ret = this.fin != null && this.start != null && this.update != null;
    return ret;
  }
  


} 
