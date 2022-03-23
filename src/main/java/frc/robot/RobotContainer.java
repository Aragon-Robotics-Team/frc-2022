// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TimedAutoLine;
import frc.robot.commands.teleop.ArcadeDrive;
import frc.robot.commands.teleop.slingshot.AutoDelay;
import frc.robot.commands.teleop.slingshot.AutoLatch;
import frc.robot.commands.teleop.slingshot.AutoPull;
import frc.robot.commands.teleop.slingshot.LatchSlingshot;
import frc.robot.commands.teleop.slingshot.PullSlingshot;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.slingshot.Bowl;
import frc.robot.subsystems.slingshot.Latch;

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
    public static final int kClimbUpButton = 4;
    public static final int kClimbDownButton = 3;
  }

  // Joysticks & Joystick Buttons
  private Joystick m_driveJoystick = new Joystick(Config.kDriveJoystickPort);

  private JoystickButton m_highGear = new JoystickButton(m_driveJoystick, Config.kHighGearButton);
  private JoystickButton m_lowGear = new JoystickButton(m_driveJoystick, Config.kLowGearButton);
  private JoystickButton m_climbUp = new JoystickButton(m_driveJoystick, Config.kClimbUpButton);
  private JoystickButton m_climbDown = new JoystickButton(m_driveJoystick, Config.kClimbDownButton);
  private JoystickButton m_bowlDown = new JoystickButton(m_driveJoystick, 7);
  private JoystickButton m_bowlUp = new JoystickButton(m_driveJoystick, 8);
  private JoystickButton m_latchOpen = new JoystickButton(m_driveJoystick, 5);
  private JoystickButton m_latchClosed = new JoystickButton(m_driveJoystick, 6);

  // The robot's subsystems and commands are defined here...
  private Drivetrain m_drivetrain = new Drivetrain();
  private Climb m_climb = new Climb();
  private Bowl m_bowl = new Bowl();
  private Latch m_latch = new Latch();

  private ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_driveJoystick);
  private TimedAutoLine m_tarmac = new TimedAutoLine(m_drivetrain);
  private LatchSlingshot m_latchSling = new LatchSlingshot(m_bowl, m_latch, m_bowlUp);
  private PullSlingshot m_pullSling = new PullSlingshot(m_bowl, m_bowlDown);
  private AutoLatch m_autoLatch = new AutoLatch(m_bowl, m_latch);
  private AutoPull m_autoPull = new AutoPull(m_bowl, m_latch);
  private AutoDelay m_autoDelay = new AutoDelay();

  private Compressor m_compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * 
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_highGear.whenPressed(m_drivetrain.getHighGear());
    m_lowGear.whenPressed(m_drivetrain.getLowGear());
    m_climbUp.whenPressed(m_climb.getUp());
    m_climbDown.whenPressed(m_climb.getDown());
    // m_bowlDown.whenPressed(m_bowl.bowlDown());
    // m_bowlDown.whenReleased(m_bowl.stopBowl());
    // m_bowlUp.whenPressed(m_bowl.bowlUp());
    // m_bowlUp.whenReleased(m_bowl.stopBowl());
    m_latchOpen.whenPressed(m_latch.getOpenLatch());
    m_latchClosed.whenPressed(m_latch.getCloseLatch());
    SmartDashboard.putData("Bowl/beginLatch", m_latchSling);
    SmartDashboard.putData("Bowl/pullLatch", m_pullSling);
  }

  public Command getInitCommand() {
    m_bowl.resetEncoder();
    return new InstantCommand(() -> m_compressor.enableDigital());
  }

  public Command getDisabledCommand() {
    return new InstantCommand(() -> m_compressor.disable());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    m_bowl.resetEncoder();
    // return new SequentialCommandGroup(m_autoLatch, m_autoPull);
    return new SequentialCommandGroup(m_autoDelay, m_tarmac);
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
