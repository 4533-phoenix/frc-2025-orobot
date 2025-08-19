package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

/** Subsystem that controls the climbing mechanism. */
public class Climb extends SubsystemBase {
  /** Singleton instance of the CLimb subsystem. */
  private static Climb instance;

  /**
   * Gets the singleton instance of the Climb subsystem.
   *
   * @return The singleton instance.
   */
  public static Climb getInstance() {
    if (instance == null) {
      instance = new Climb();
    }
    return instance;
  }

  private DoubleSolenoid climbSolenoid = null;
  private NetworkTable table;

  private Climb() {
    climbSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClimbConstants.CLIMB_SOLENOID_DEPLOY, ClimbConstants.CLIMB_SOLENOID_RETRACT);
    table = NetworkTableInstance.getDefault().getTable("Robot").getSubTable("Climb");
  }

  public void extendCylinder(){
     climbSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void retractCylinder(){
     climbSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  @Override
  public void periodic() {}
  
   public Command climb() {
     return run(() -> {
          extendCylinder();
        })
        .withName("climb");
   }

  /**
   * Stops the climb.
   *
   * @return A command that stops the climb.
   */
  public Command stop() {
    return runOnce(
            () -> {
              retractCylinder();
            })
        .withName("stop");
  }
}
