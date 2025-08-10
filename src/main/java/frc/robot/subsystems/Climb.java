package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Value;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.RobotContainer;
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

  private Compressor compressor = new Compressor(ClimbConstants.PCM_ID ,PneumaticsModuleType.CTREPCM);
  private DoubleSolenoid climbSolenoid = null;
  
  private Climb() {
  /*********TODO: what module type are we using?? *******/
    climbSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClimbConstants.SHOOTER_PITCH_SOLENOID_DEPLOY, ClimbConstants.SHOOTER_PITCH_SOLENOID_RETRACT);
  }

  public void enableCompressor() {
    compressor.enableDigital();
}

public void disableCompressor() {
    compressor.disable();
}

public void pitchUp(){
   climbSolenoid.set(DoubleSolenoid.Value.kForward);
}

public void pitchDown(){
   climbSolenoid.set(DoubleSolenoid.Value.kReverse);
}
@Override
public void periodic() {}
  
   public Command climb() {
     return run(() -> {
          enableCompressor();
          pitchUp();
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
              disableCompressor();
              pitchDown();
            })
        .withName("stop");
  }
}
