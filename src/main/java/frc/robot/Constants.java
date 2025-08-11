package frc.robot;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radian;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import frc.robot.helpers.PID;

import java.util.Arrays;
import java.util.Map;

/** Constants used throughout the robot code. */
public final class Constants {
  private Constants() {}

  /** Constants for basic robot characteristics. */
  public static final class RobotConstants {
    private RobotConstants() {}

    /** Maximum speed of the robot in meters per second. */
    public static final LinearVelocity MAX_SPEED = MetersPerSecond.of(5.45);
  }

  /** Constants for the climbing mechanism. */
  public static final class ClimbConstants {
    /** Private constructor to prevent instantiation. */
    private ClimbConstants() {}

    /** CAN ID for the climb motor. */
    public static final int CAN_ID = 14;

    /** Bottom limit channel for the climb. */
    public static final int BOTTOM_LIMIT_CHANNEL = 9;

    /** Top limit channel for the climb. */
    public static final int TOP_LIMIT_CHANNEL = 8;

    /** Climb power for the climb. */
    public static final double CLIMB_POWER = -0.8;

    /** Whether the climb is inverted. */
    public static final boolean IS_INVERTED = false;
  }

  public static final class CoralIntakeConstants {
    private CoralIntakeConstants() {}

    public static final int INTAKE_MOTOR_CAN_ID = 1;
    public static final int CORAL_PRESENCE_LIMIT_CHANNEL = 1;
    public static final double CORAL_INTAKE_POWER = -0.5;
  }

  /** Constants for operator interface (OI). */
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
