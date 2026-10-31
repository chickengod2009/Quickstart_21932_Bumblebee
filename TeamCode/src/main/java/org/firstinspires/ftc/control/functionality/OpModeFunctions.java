package org.firstinspires.ftc.control.functionality;

import org.firstinspires.ftc.teamcode.opModeFuncs.telopFuncs.Teleop;

import org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs.FlywheelTest;
import org.firstinspires.ftc.teamcode.subsystems.Util;

import java.util.function.Consumer;

/**
*An Idea could to be to create a set of Auton Classes and put the coresponding static functions here in the the enum params
 **/ 
public enum OpModeFunctions {


  TELOP(Teleop::start,Teleop::update,Teleop::end),


  AUTON_BLUE_ONE(OpModeFunctions::todo, OpModeFunctions::todo, OpModeFunctions::todo),
  

  AUTON_RED_ONE(OpModeFunctions::todo, OpModeFunctions::todo, OpModeFunctions::todo),

  
  TEST_1(FlywheelTest::init, FlywheelTest::loop, OpModeFunctions::todo);

  public final Consumer<Util> start;
  public final Consumer<Util> update;
  public final Consumer<Util> fin;
  
  private OpModeFunctions(Consumer<Util> star, Consumer<Util> up, Consumer<Util> finish){
    start =star;
    update = up;
    fin = finish;
                    
  }


  public static void todo(Util ut) {throw Util.TODO();}

//  private ArrayList<Pose> poses = null;
//  private ArrayList<Path> paths = null;


  

  public boolean isSafe(){
    boolean ret = this.fin != null && this.start != null && this.update != null;
    return ret;
  }
  


} 
