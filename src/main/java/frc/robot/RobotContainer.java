// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.driving.ArcadeDrive;
import frc.robot.commands.driving.DiffDriveIdle;
import frc.robot.commands.driving.MoveWithPID;
import frc.robot.commands.driving.TurnTowardCargo;
import frc.robot.commands.shooting.MoveTowardTape;
import frc.robot.sensors.Gyroscope;
import frc.robot.sensors.PixyCamera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.shooter.Latch;

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

    public static final double kTarmacSetpoint = 5.0;
  }

  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Limelight m_limelight = new Limelight();
  private final PixyCamera m_pixy = new PixyCamera();
  private final Gyroscope m_gyro = new Gyroscope();
  private final Latch m_latch = new Latch();

  private final Joystick m_joystick = new Joystick(Config.kdriveJoystickPort);

  private final JoystickButton m_aButton = new JoystickButton(m_joystick, 2);
  private final JoystickButton m_xButton = new JoystickButton(m_joystick, 1);
  private final JoystickButton m_rtButton = new JoystickButton(m_joystick, 8);

  // private final Command m_testPixy = new TestPixy(m_pixy);
  private final Command m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_joystick);
  private final Command m_tarmac = new MoveWithPID(m_drivetrain, Config.kTarmacSetpoint);
  private final Command m_diffIdle = new DiffDriveIdle(m_drivetrain);
  private final Command m_hub = new MoveTowardTape(m_drivetrain, m_limelight);
  private final Command m_cargo = new TurnTowardCargo(m_drivetrain, m_pixy, m_rtButton);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_gyro.reset();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_xButton.whenPressed(m_hub);
    m_aButton.whenPressed(m_cargo);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    m_drivetrain.setDefaultCommand(m_diffIdle);

    return m_tarmac;
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    m_gyro.reset();
    m_drivetrain.resetEncoder();
    m_drivetrain.setIdleMode(NeutralMode.Coast);
    m_drivetrain.setDefaultCommand(m_arcadeDrive);

    return null;
  }
}
