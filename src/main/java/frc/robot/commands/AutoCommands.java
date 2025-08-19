package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Swerve;

/**
 * Container for autonomous command sequences used during the autonomous period.
 * This class provides
 * factory methods that create and return command sequences for different
 * autonomous routines. Each
 * method constructs a complete autonomous strategy that can be selected and run
 * during the
 * autonomous phase.
 */
public class AutoCommands {
  private AutoCommands() {
    // Utility class - prevent instantiation since all methods are static
  }

  /**
   * Creates a simple test command that drives the robot backward at a fixed speed
   * for a set time.
   * Useful for testing basic autonomous movement and verifying drivetrain
   * functionality.
   *
   * @return A command that drives backward at 1.0 m/s for 3 seconds and then
   *         stops
   */
  public static Command simpleBackwardDrive() {
    Swerve swerve = Swerve.getInstance();

    return Commands.run(
        () -> {
          // Drive backward at 1.0 m/s in the negative x direction (robot-relative)
          swerve
              .getSwerveDrive()
              .setChassisSpeeds(
                  new ChassisSpeeds(-1.0, 0, 0) // x velocity, y velocity, rotational velocity
          );
        },
        swerve)
        .withTimeout(3.0) // Run for exactly 3 seconds
        .andThen(
            () -> {
              // Stop all robot movement by setting velocities to zero
              swerve.getSwerveDrive().setChassisSpeeds(new ChassisSpeeds(0, 0, 0));
            });
  }
}
