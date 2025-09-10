package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.CoralIntakeConstants;

public class CoralIntake extends SubsystemBase {
    private static CoralIntake instance;

    public static CoralIntake getInstance() {
        if (instance == null)
            instance = new CoralIntake();

        return instance;
    }

    /** The intake motor */
    private SparkMax intakeMotor;
    /** The coral presence limit switch */
    private DigitalInput coralPresenceLimit;

    private CoralIntake() {
        intakeMotor = new SparkMax(CoralIntakeConstants.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
        coralPresenceLimit = new DigitalInput(CoralIntakeConstants.CORAL_PRESENCE_LIMIT_CHANNEL);
    }

    public final Trigger highOutputCurrent = new Trigger(() -> intakeMotor.getOutputCurrent() > 15.0);

    /** Runs the coral intake motor until interrupted, then stops the motor */
    public Command intake() {
        return Commands.runEnd(
            () -> { intakeMotor.set(CoralIntakeConstants.CORAL_INTAKE_POWER); },
            () -> { intakeMotor.set(0); }
        )
                .withName("coralIntake");
    }

    /** Runs the coral intake motor in reverse to score it until interrupted, then stops the motor */
    public Command scoreCoral() {
        return Commands.runEnd(
            () -> { intakeMotor.set(CoralIntakeConstants.CORAL_SCORE_POWER); },
            () -> { intakeMotor.set(0); }
        )
                .withName("scoreCoral");
    }

    /** Stops the coral intake motor */
    public Command stop() {
        return Commands.runOnce(() -> intakeMotor.set(0))
                .withName("stop");
    }
}
