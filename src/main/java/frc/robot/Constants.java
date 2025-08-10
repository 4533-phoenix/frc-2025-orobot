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
/*************TODO: figure out real constant values for robot********************/
  /** Constants for the climbing mechanism. */
  public static final class ClimbConstants {
    /** Private constructor to prevent instantiation. */
    private ClimbConstants() {}

    /** PCM ID for the Climb pneumatics. */
    public static final int PCM_ID = 14;

    /** Constants for Climb Solenoid */
    public static final int CLIMB_SOLENOID_DEPLOY = 0;
    public static final int CLIMB_SOLENOID_RETRACT = 1;

  }

  /** Contatnts for coral Intake */
  public static final class IntakeConstants {
    private IntakeConstants() {}
     
    // Intake motor Constants
    /** CAN ID for intake motor. */
    /*** TODO: find out real values *****/
    public static final int CAN_ID = 17;

    /** DIO channel for coral sensor */
    public static final int SENSOR_CHANNEL = 1;

    /** Power level for intake operation */
    public static final double INTAKE_POWER = 0.2;

    /** Power level for dropping coral */
    public static final double DROP_POWER = 1.0;

    /** Power level for ejecting stuck objects */
    public static final double EJECT_POWER = 0.5;

    /** Whether manipulator motor is inverted */
    public static final boolean INVERTED = false;

    /** Current limit for manipulator motor in amps */
    public static final int CURRENT_LIMIT = 20;

    /** Ramp rate for manipulator motor (seconds from 0 to full throttle) */
    public static final double RAMP_RATE = 0.1;

    /** PCM ID for the Intake pneumatics. */
    public static final int PCM_ID = 14;

    /** Constants for Intake Solenoid */
    public static final int INTAKE_SOLENOID_DEPLOY = 0;
    public static final int INTAKE_SOLENOID_RETRACT = 1;



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
