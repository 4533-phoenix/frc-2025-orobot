package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.*;
import frc.robot.helpers.CustomSwerveInput;
import frc.robot.subsystems.*;

/**
 * Main robot configuration class that binds controls and commands to subsystems. This class serves
 * as the robot's command center, managing all subsystem instances and their associated commands.
 *
 * <p>Features include:
 *
 * <ul>
 *   <li>Driver control configuration
 *   <li>Command button mappings
 *   <li>Autonomous command selection
 *   <li>Subsystem instantiation and management
 * </ul>
 *
 * <p>The class follows a centralized control pattern, with all robot behaviors defined through
 * command bindings and default commands.
 */
public class RobotContainer {
  /** Network table for robot-related bs */
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Robot");

  /** Xbox controller used for driver input. */
  private final CommandXboxController driverController =
      new CommandXboxController(OIConstants.DRIVER_CONTROLLER_PORT);

  /** "Xbox" controller used for operator input. */
  private final CommandXboxController operatorController = new CommandXboxController(1);

  /** Main drive subsystem for robot movement. */
  private final Swerve swerveDrive = Swerve.getInstance();

  /**
   * Input stream for swerve drive control. Configures how controller inputs are processed and
   * applied to drive commands.
   */
  private final CustomSwerveInput driveInputStream =
      CustomSwerveInput.of(
              swerveDrive.getSwerveDrive(),
              () -> driverController.getLeftY() * -1,
              () -> driverController.getLeftX() * -1)
          .cubeTranslationControllerAxis(true)
          .scaleTranslation(0.75)
          .scaleTranslation(() -> driverController.rightBumper().getAsBoolean(), 0.5)
          .withControllerHeadingAxis(
              () -> driverController.getRightX() * -1, () -> driverController.getRightY() * -1)
          .cubeRotationControllerAxis(true)
          .deadband(OIConstants.DRIVER_DEADBAND)
          .allianceRelativeControl(true)
          .headingWhile(true);

  // private final SwerveInputStream driveInputStream =
  // SwerveInputStream.of(swerveDrive.getSwerveDrive(),
  // () -> driverController.getLeftY() * -1,
  // () -> driverController.getLeftX() * -1)
  // .cubeTranslationControllerAxis(true)
  // .scaleTranslation(1.0)
  // .withControllerRotationAxis(() -> driverController.getLeftX() * -1)
  // .cubeRotationControllerAxis(true)
  // .deadband(OIConstants.DRIVER_DEADBAND)
  // .allianceRelativeControl(true);

  /** Climb subsystem for handling climb mechanism. */
  private final Climb climb = Climb.getInstance();

  // Add the SendableChooser for autonomous
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  // Trigger for endgame
  public final Trigger endgame = new Trigger(() -> DriverStation.getMatchTime() <= 30);

  /**
   * Creates a new RobotContainer and initializes all robot subsystems and commands. Performs the
   * following setup:
   *
   * <ul>
   *   <li>Silences joystick warnings for unplugged controllers
   *   <li>Disables controller rumble
   *   <li>Configures button bindings for commands
   * </ul>
   */
  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);
    driverController.setRumble(RumbleType.kBothRumble, 0.0);
    operatorController.setRumble(RumbleType.kBothRumble, 0.0);

    configureBindings();
    configureAutoChooser();
  }

  /**
   * Configures button bindings for commands. Maps controller buttons to specific robot actions
   * organized by controller and function.
   */
  private void configureBindings() {
    endgame.onTrue(
        Commands.sequence(
            Commands.runOnce(() -> driverController.setRumble(RumbleType.kLeftRumble, 1.0)),
            Commands.waitSeconds(0.5),
            Commands.runOnce(() -> driverController.setRumble(RumbleType.kLeftRumble, 0.0))));

    configureDriverControls();
    configureOperatorControls();
  }

  /**
   * Configure driver controller bindings for drivetrain controls, autonomous features, and climb
   */
  private void configureDriverControls() {
    // DEFAULT COMMAND - Field-oriented drive with automatic heading
    Command driveFieldOrientedDirectAngle =
        swerveDrive.driveFieldOriented(
            driveInputStream
                .copy()
                .headingWhile(true));
    swerveDrive.setDefaultCommand(driveFieldOrientedDirectAngle);

    // CLIMBING CONTROL
    driverController.x().whileTrue(climb.climb());
  }

  /** Configure operator controller bindings for game piece and mechanism controls */
  private void configureOperatorControls() {
  }

  /** Configure the autonomous command chooser with available options. */
  private void configureAutoChooser() {
    autoChooser.setDefaultOption("None", Commands.none());
    autoChooser.addOption("Simple Backward Drive", AutoCommands.simpleBackwardDrive());
  }

  /**
   * Provides the command to run during autonomous mode. Currently returns a placeholder command
   * that prints a message, indicating no autonomous routine is configured.
   *
   * @return the command to run in autonomous mode
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  /**
   * Returns the NetworkTable used for debugging.
   *
   * @return the NetworkTable used for debugging
   */
  public NetworkTable getTable() {
    return table;
  }
}
