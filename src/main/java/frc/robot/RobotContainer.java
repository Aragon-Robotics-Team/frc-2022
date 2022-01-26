// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.shooter.Flywheel;
import frc.robot.commands.driving.ArcadeDrive;
import frc.robot.commands.driving.testing.TestPixy;
import frc.robot.sensors.PixyCamera;
import frc.robot.commands.shooting.RampFlywheel;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static final class Config {
    public static final int kdriveJoystickPort = 4;
   
  }



  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Joystick m_joystick = new Joystick(Config.kdriveJoystickPort);
  private final ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_joystick);
  private final PixyCamera m_pixy = new PixyCamera();

  private final Command m_testPixy = new TestPixy(m_pixy);
  private final Flywheel m_Flywheel = new Flywheel();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    m_pixy.setDefaultCommand(m_testPixy);

    return null;
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    return m_arcadeDrive;
  }
}
