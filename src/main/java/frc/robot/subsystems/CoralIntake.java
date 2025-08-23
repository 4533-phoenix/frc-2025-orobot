package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
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

    public final Trigger coralPresent = new Trigger(() -> !coralPresenceLimit.get());

    /** Runs the coral intake motor until a coral is detected */
    public Command intake() {
        return Commands.run(() -> intakeMotor.set(CoralIntakeConstants.CORAL_INTAKE_POWER))
                .onlyWhile(coralPresent.negate())
                .andThen(Commands.sequence(
                    stop(),
                    CoralArm.getInstance().scorePosition()
                ))
                .withName("coralIntake");
    }

    /** Runs the coral intake motor in reverse to score it */
    public Command scoreCoral() {
        return Commands.run(() -> intakeMotor.set(CoralIntakeConstants.CORAL_SCORE_POWER))
                .onlyWhile(coralPresent)
                .andThen(stop())
                .withName("scoreCoral");
    }

    /** Stops the coral intake motor */
    public Command stop() {
        return Commands.runOnce(() -> intakeMotor.set(0))
                .withName("stop");
    }
}
