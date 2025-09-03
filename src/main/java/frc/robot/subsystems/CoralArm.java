package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.CoralIntakeConstants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;

/**
 * Subsystem that controls the Coral Arm, responsible for moving the arm to
 * different positions.
 */
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

  private Solenoid intakeSolenoid;
  private NetworkTable table;

  private CoralArm() {
    intakeSolenoid = Pneumatics.getInstance().getSolenoid(CoralIntakeConstants.INTAKE_SOLENOID_CHANNEL);

    table = NetworkTableInstance.getDefault().getTable("Robot").getSubTable("Intake");
  }

  /**
   * set cylinder to a position
   * 
   * @param boolean The desired position value
   * @return A command that extends the cylinder and puts the arm in intake
   *         position.
   */
  private Command setPosition(boolean value) {
    Trigger notInPos = new Trigger(() -> intakeSolenoid.get() != value);

    return Commands.either(
      Commands.sequence(
        Commands.runOnce(() -> {
          intakeSolenoid.set(value);
        }),
        Commands.waitSeconds(0.3)
      ),
      Commands.none(),
      notInPos
    )
        .withName("setPosition");
  }

  /**
   * extend cylinder for intake position
   * 
   * @return A command that extends the cylinder and puts the arm in intake
   *         position.
   */
  public Command intakePosition() {
    return setPosition(CoralIntakeConstants.INTAKE_POS_VAL);
  }

  /**
   * retract cylinder for scoring position
   *
   * @return A command that retracts the cylinder and puts the arm in scoring
   *         position.
   */
  public Command scorePosition() {
    return setPosition(CoralIntakeConstants.SCORE_POS_VAL);
  }

  @Override
  public void periodic() {
  }
}