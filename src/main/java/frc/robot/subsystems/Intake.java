package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.IntakeConstants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;


/** Subsystem that controls the Coral Arm, responsible for moving the arm to different positions. */
public class Intake extends SubsystemBase {
  private static Intake instance;

  /**
   * Gets the singleton instance of the CoralArm subsystem.
   *
   * @return The singleton instance.
   */
  public static Intake getInstance() {
    if (instance == null) {
      instance = new Intake();
    }
    return instance;
  }
  private DoubleSolenoid intakeSolenoid = null;
  private SparkMax coralMotor;
  private DigitalInput coralSensor;
  private NetworkTable table;

  private Intake() {
    /*********TODO: what module type are we using?? *******/
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, IntakeConstants.INTAKE_SOLENOID_DEPLOY, IntakeConstants.INTAKE_SOLENOID_RETRACT);

    coralMotor = new SparkMax(IntakeConstants.CAN_ID, MotorType.kBrushless);
    coralSensor = new DigitalInput(IntakeConstants.SENSOR_CHANNEL);
  
    table = NetworkTableInstance.getDefault().getTable("Robot").getSubTable("Intake");

    SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();
    sparkMaxConfig.idleMode(IdleMode.kBrake);
    sparkMaxConfig.inverted(IntakeConstants.INVERTED);
    sparkMaxConfig.smartCurrentLimit(30);
    coralMotor.configure(
        sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    setDefaultCommand(stop());
  }

  /** Trigger that is active when the Coral Manipulator is running. */
  public final Trigger running = new Trigger(() -> coralMotor.get() != 0);

  /** Trigger that is active when the Coral Manipulator does not have a coral. */
  public final Trigger doesntHaveCoral = new Trigger(() -> coralSensor.get()).debounce(0.25);

  /** Trigger that is active when the Coral Manipulator has a coral. */
  public final Trigger hasCoral = new Trigger(() -> !coralSensor.get()).debounce(0.1);

  public void extendCylinder(){
    intakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }
 
 public void retractCylinder(){
    intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
 }

 public Command intakeCoral(){
    return run(() -> coralMotor.set(IntakeConstants.INTAKE_POWER))
        .onlyWhile(doesntHaveCoral)
        .andThen(instantStop());
 }

 public Command scoreCoral(){
    return run(() -> coralMotor.set(IntakeConstants.DROP_POWER))
        .onlyWhile(hasCoral)
        .andThen(instantStop());
 }

 /**
   * Stops the coral manipulator.
   *
   * @return A command that stops the coral manipulator.
   */
  public Command stop() {
    return run(() -> stopMotor());
  }

  /**
   * Instantly stops the coral manipulator.
   *
   * @return A command that instantly stops the coral manipulator.
   */
  public Command instantStop() {
    return runOnce(() -> stopMotor());
  }

  private void stopMotor() {
    coralMotor.set(0.0);
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
    table.getEntry("running").setBoolean(running.getAsBoolean());
    table.getEntry("hasCoral").setBoolean(hasCoral.getAsBoolean());
  }
}