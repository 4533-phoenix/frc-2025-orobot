package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.CoralIntakeConstants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;


/** Subsystem that controls the Coral Arm, responsible for moving the arm to different positions. */
public class CoralArm extends SubsystemBase {
  private static CoralArm instance;

  /**
   * Gets the singleton instance of the CoralArm subsystem.
   *
   * @return The singleton instance.
   */
  public static CoralArm getInstance() {
    if (instance == null) {
      instance = new CoralArm();
    }
    return instance;
  }
  private DoubleSolenoid intakeSolenoid = null;
  private NetworkTable table;

  private CoralArm() {
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, CoralIntakeConstants.INTAKE_SOLENOID_DEPLOY, CoralIntakeConstants.INTAKE_SOLENOID_RETRACT);
  
    table = NetworkTableInstance.getDefault().getTable("Robot").getSubTable("Intake");

  }

  public void extendCylinder(){
    intakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }
 
 public void retractCylinder(){
    intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
 }

/**
 * extend cylinder for intake position
 * 
 * @return A command that extends the cylinder and puts the arm in intake position.
 */
 public Command intakePosition() {
    return run(() -> {
         extendCylinder();
       })
       .withName("intakePosition");
  }

 /**
  * retract cylinder for scoring position
  *
  * @return A command that retracts the cylinder and puts the arm in scoring position.
  */
 public Command scorePosition() {
   return run(
           () -> {
             retractCylinder();
           })
       .withName("scorePosition");
 } 

  @Override
  public void periodic() {
    
  }
}