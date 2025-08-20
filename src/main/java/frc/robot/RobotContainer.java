package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.*;
import frc.robot.helpers.CustomSwerveInput;
import frc.robot.subsystems.*;

/**
 * Main robot configuration class that binds controls and commands to
 * subsystems. This class serves
 * as the robot's command center, managing all subsystem instances and their
 * associated commands.
 *
 * <p>
 * Features include:
 *
 * <ul>
 * <li>Driver control configuration
 * <li>Command button mappings
 * <li>Autonomous command selection
 * <li>Subsystem instantiation and management
 * </ul>
 *
 * <p>
 * The class follows a centralized control pattern, with all robot behaviors
 * defined through
 * command bindings and default commands.
 */
public class RobotContainer {
  /** Network table for robot-related bs */
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Robot");

  /** Xbox controller used for driver input. */
  private final CommandXboxController driverController = new CommandXboxController(OIConstants.DRIVER_CONTROLLER_PORT);

  /** "Xbox" controller used for operator input. */
  private final CommandXboxController operatorController = new CommandXboxController(1);

  /** Main drive subsystem for robot movement. */
  private final Swerve swerveDrive = Swerve.getInstance();

  /**
   * Input stream for swerve drive control. Configures how controller inputs are
   * processed and
   * applied to drive commands.
   */
  private final CustomSwerveInput driveInputStream = CustomSwerveInput.of(
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

  /** Coral intake subsystem */
  // private final CoralIntake coralIntake = CoralIntake.getInstance();

  /** Pneumatics for Climb **/
  public final Pneumatics pneumatics = Pneumatics.getInstance();

  /** Climb subsystem for handling climb mechanism. **/
  private final Climb climb = Climb.getInstance();

  /** CoralArm subsystem for handling coral arm **/
  private final CoralArm manipulatorCoralArm = CoralArm.getInstance();

  /** CoralIntake subsystem for handling coral intake */
  private final CoralIntake intake = CoralIntake.getInstance();

  // Add the SendableChooser for autonomous
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  // Trigger for endgame
  public final Trigger endgame = new Trigger(() -> DriverStation.getMatchTime() <= 30);

  /**
   * Creates a new RobotContainer and initializes all robot subsystems and
   * commands. Performs the
   * following setup:
   *
   * <ul>
   * <li>Silences joystick warnings for unplugged controllers
   * <li>Disables controller rumble
   * <li>Configures button bindings for commands
   * </ul>
   */
  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);
    driverController.setRumble(RumbleType.kBothRumble, 0.0);
    operatorController.setRumble(RumbleType.kBothRumble, 0.0);

    configureBindings();
    configureAutoChooser();

    // initialize pnuematic compressor
  }

  /**
   * Configures button bindings for commands. Maps controller buttons to specific
   * robot actions
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
   * Configure driver controller bindings for drivetrain controls, autonomous
   * features, and climb
   */
  private void configureDriverControls() {
    // DEFAULT COMMAND - Field-oriented drive with automatic heading
    // Command driveFieldOrientedDirectAngle =
    // swerveDrive.driveFieldOriented(
    // driveInputStream
    // .copy()
    // .headingWhile(true));
    Command justpleasework = swerveDrive.driveFieldOriented(
        driveInputStream);
    swerveDrive.setDefaultCommand(justpleasework);

    // CLIMBING CONTROL
    driverController.y().onTrue(climb.climb());
    driverController.a().onTrue(climb.stop());
  }

  /**
   * Configure operator controller bindings for game piece and mechanism controls
   */
  private void configureOperatorControls() {
    // ---- CORAL MANIPULATOR CONTROLS ----
    operatorController.a().onTrue(intake.stop());
    operatorController.x().onTrue(intake.scoreCoral());

    operatorController.leftTrigger().onTrue(
      Commands.sequence(
        manipulatorCoralArm.intakePosition(),
        Commands.waitSeconds(0.3),
        intake.intake()
      )
    );
    operatorController.rightTrigger().onTrue(
        intake.scoreCoral()
    );

    // ---- CORAL ARM POSITION CONTROLS ----
    // Each of these stops the manipulator before moving to ensure safe operation

    // Set to score position
    operatorController
        .y()
        .onTrue(
            manipulatorCoralArm.scorePosition());

    // Set to intake postion
    operatorController
        .b()
        .onTrue(
            manipulatorCoralArm.intakePosition());

  }

  /** Configure the autonomous command chooser with available options. */
  private void configureAutoChooser() {
    autoChooser.setDefaultOption("None", Commands.none());
    autoChooser.addOption("Simple Backward Drive", AutoCommands.simpleBackwardDrive());
  }

  /**
   * Provides the command to run during autonomous mode. Currently returns a
   * placeholder command
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
