package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import edu.wpi.first.units.measure.LinearVelocity;

/** Constants used throughout the robot code. */
public final class Constants {
  private Constants() {}

  /** Constants for basic robot characteristics. */
  public static final class RobotConstants {
    private RobotConstants() {}

    /** Maximum speed of the robot in meters per second. */
    public static final LinearVelocity MAX_SPEED = MetersPerSecond.of(2.0);
  }
/*************TODO: figure out real constant values for robot********************/
  /** Constants for the climbing mechanism. */
  public static final class ClimbConstants {
    /** Private constructor to prevent instantiation. */
    private ClimbConstants() {}

    /** PCM ID for the Climb pneumatics. */
    public static final int PCM_ID = 32;

    /** Constants for Climb Solenoid */
    public static final int CLIMB_SOLENOID_DEPLOY = 0;
    public static final int CLIMB_SOLENOID_RETRACT = 1;

  }

  public static final class CoralIntakeConstants {
    private CoralIntakeConstants() {}

    public static final int INTAKE_MOTOR_CAN_ID = 1;
    public static final int CORAL_PRESENCE_LIMIT_CHANNEL = 0;
    public static final double CORAL_INTAKE_POWER = -0.5;
    public static final double CORAL_SCORE_POWER = 0.5;

    
    /** PCM ID for the Intake pneumatics. */
    public static final int PCM_ID = 14;

    /** Constants for Intake Solenoid */
    public static final int INTAKE_SOLENOID_DEPLOY = 0;
    public static final int INTAKE_SOLENOID_RETRACT = 1;
  }

  /* Constants for operator interface (OI). */
  public static final class OIConstants {
    private OIConstants() {}

    /** Driver controller port. */
    public static final int DRIVER_CONTROLLER_PORT = 0;

    /** Deadband for driver controller input. */
    public static final double DRIVER_DEADBAND = 0.002;
  }

  /** Constants for field positions and points of interest */
  public static final class FieldConstants {
    private FieldConstants() {}

    /** Field dimensions for 2025 Reefscape */
    public static final double FIELD_LENGTH_METERS = 17.548;

    /** Field width in meters. */
    public static final double FIELD_WIDTH_METERS = 8.052;
  }
}
