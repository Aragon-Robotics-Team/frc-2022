// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.MoveWithPID;
import frc.robot.commands.teleop.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static final class Config {
    public static final int kDriveJoystickPort = 4;
    public static final int kHighGearButton = 1;
    public static final int kLowGearButton = 2;
    public static final int kPCMId = 1;

    public static final double kAutoPIDSetpoint = 5.0;
  }

  // Joysticks & Joystick Buttons
  private Joystick m_driveJoystick = new Joystick(Config.kDriveJoystickPort);

  private JoystickButton m_highGear = new JoystickButton(m_driveJoystick, Config.kHighGearButton);
  private JoystickButton m_lowGear = new JoystickButton(m_driveJoystick, Config.kLowGearButton);

  // The robot's subsystems and commands are defined here...
  private Drivetrain m_drivetrain = new Drivetrain();

  private ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_driveJoystick);
  private MoveWithPID m_tarmac = new MoveWithPID(m_drivetrain, Config.kAutoPIDSetpoint);

  private Compressor m_compressor = new Compressor(Config.kPCMId);

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
    m_highGear.whenPressed(m_drivetrain.getHighGear());
    m_lowGear.whenPressed(m_drivetrain.getLowGear());
  }

  public Command getInitCommand() {
    return new InstantCommand(() -> m_compressor.start());
  }

  public Command getDisabledCommand() {
    return new InstantCommand(() -> m_compressor.stop());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_tarmac;
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    m_drivetrain.resetEncoder();
    m_drivetrain.setDefaultCommand(m_arcadeDrive);

    return null;
  }
}
