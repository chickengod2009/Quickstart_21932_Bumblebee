package org.firstinspires.ftc.teamcode.control.functionality;

import org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs.Auton_One;
import org.firstinspires.ftc.teamcode.opModeFuncs.autoFuncs.Auton_Two;
import org.firstinspires.ftc.teamcode.opModeFuncs.telopFuncs.TeleopFunctions;

import org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs.FullTest;
import org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs.IntakeTest;
import org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs.ShooterCamDistTest;
import org.firstinspires.ftc.teamcode.opModeFuncs.testFuncs.ShooterGateIntakeTestFunc;
import org.firstinspires.ftc.teamcode.subsystems.Master;

import java.util.function.Consumer;

/**
*An Idea could to be to create a set of Auton Classes and put the coresponding static functions here in the the enum params
 **/ 
public enum OpModeFunctions {


  TELOP(TeleopFunctions::init, TeleopFunctions::start, TeleopFunctions::update, TeleopFunctions::end),


  AUTON_ONE(Auton_One::init, Auton_One::start, Auton_One::loop, Auton_One::fin),
  

  AUTON_TWO(Auton_Two::init, Auton_Two::start,Auton_Two::loop, Auton_Two::fin),


  DIST_TEST(ShooterCamDistTest::init, ShooterCamDistTest::start, ShooterCamDistTest::loop, ShooterCamDistTest:: end),
  TEST_1(IntakeTest::init, OpModeFunctions::todo, IntakeTest::loop, OpModeFunctions::todo),
  FULL_TEST(FullTest::init, OpModeFunctions::todo, FullTest::loop, OpModeFunctions::todo ),
  Intake_Gate_Shooter_Test(ShooterGateIntakeTestFunc::init, OpModeFunctions::todo, ShooterGateIntakeTestFunc::loop, OpModeFunctions::todo );

  public final Consumer<MasterTrait> start;
  public final Consumer<MasterTrait> update;
  public final Consumer<MasterTrait> fin;

  public final Consumer<MasterTrait> init;
  
  private OpModeFunctions(Consumer<MasterTrait> init, Consumer<MasterTrait> star, Consumer<MasterTrait> up, Consumer<MasterTrait> finish){
    start =star;
    update = up;
    fin = finish;
    this.init =init;
                    
  }


  public static void todo(MasterTrait ut) {throw Master.TODO();}

//  private ArrayList<Pose> poses = null;
//  private ArrayList<Path> paths = null;


  

  public boolean isSafe(){
    boolean ret = this.fin != null && this.start != null && this.update != null;
    return ret;
  }



} 
