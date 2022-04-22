// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.TestClimb;
import frc.robot.commands.auto.FullAuto;
import frc.robot.commands.teleop.ArcadeDrive;
import frc.robot.commands.teleop.slingshot.AutoLatch;
import frc.robot.commands.teleop.slingshot.AutoPull;
import frc.robot.commands.teleop.slingshot.TestWinch;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FeedIn;
import frc.robot.subsystems.intake.Hopper;
import frc.robot.subsystems.intake.Rollers;
import frc.robot.subsystems.slingshot.Winch;
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
    public static final int kShootJoystick = 5;

    public static final int kHighGearButton = 6;
    public static final int kLowGearButton = 5;
    public static final int kClimbUpButton = 4;
    public static final int kClimbDownButton = 3;
    public static final int kArmUpButton = 1;
    public static final int kArmDownButton = 4;
    // public static final int kIntakeButton = 8;
    // public static final int kHopperButton = 7;
    public static final int kFeedInButton = 2; // TODO
    public static final int kFeedOutButton = 3;
    public static final int kShooterUpButton = 9;
    public static final int kShooterDownButton = 6; // TODO
    public static final int kReleaseShooterButton = 5; // TODO
  }

  // Joysticks & Joystick Buttons
  private Joystick m_driveJoystick = new Joystick(Config.kDriveJoystickPort);
  private Joystick m_shootJoystick = new Joystick(Config.kShootJoystick);
  private Joystick m_testJoystick = new Joystick(2);

  private JoystickButton m_highGear = new JoystickButton(m_driveJoystick, Config.kHighGearButton);
  private JoystickButton m_lowGear = new JoystickButton(m_driveJoystick, Config.kLowGearButton);
  private JoystickButton m_climbUp = new JoystickButton(m_driveJoystick, Config.kClimbUpButton);
  private JoystickButton m_climbDown = new JoystickButton(m_driveJoystick, Config.kClimbDownButton);
  private JoystickButton m_armUpButton = new JoystickButton(m_shootJoystick,
      Config.kArmUpButton);
  private JoystickButton m_armDownButton = new JoystickButton(m_shootJoystick,
      Config.kArmDownButton);
  private JoystickButton m_feedInButton = new JoystickButton(m_shootJoystick, Config.kFeedInButton);
  private JoystickButton m_feedOutButton = new JoystickButton(m_shootJoystick, Config.kFeedOutButton);
  private JoystickButton m_shootButton = new JoystickButton(m_shootJoystick, 8);

  private JoystickButton m_shooterUpButton = new JoystickButton(m_driveJoystick, Config.kShooterUpButton);
  private JoystickButton m_shooterDownButton = new JoystickButton(m_driveJoystick, Config.kShooterDownButton);
  private JoystickButton m_hopperButton = new JoystickButton(m_driveJoystick, 10);
  private JoystickButton m_testWinchForward = new JoystickButton(m_testJoystick, 7);
  private JoystickButton m_testWinchBackward = new JoystickButton(m_testJoystick, 8);
  private JoystickButton m_testLatchOpen = new JoystickButton(m_testJoystick, 5);
  private JoystickButton m_testLatchClosed = new JoystickButton(m_testJoystick, 6);
  private JoystickButton m_downButton = new JoystickButton(m_shootJoystick, 7);

  // The robot's subsystems and commands are defined here...
  private Drivetrain m_drivetrain = new Drivetrain();
  private Climb m_climb = new Climb();
  private Winch m_winch = new Winch();
  private Latch m_latch = new Latch();
  private Rollers m_rollers = new Rollers();
  private Hopper m_hopper = new Hopper();

  private ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_drivetrain, m_driveJoystick);
  private FullAuto m_fullAuto = new FullAuto(m_winch, m_latch, m_drivetrain);

  private Compressor m_compressor = new Compressor(1, PneumaticsModuleType.CTREPCM);
  private FeedIn m_feedIn = new FeedIn(m_rollers, m_hopper, m_feedInButton, m_feedOutButton);
  private TestWinch m_testWinch = new TestWinch(m_winch, m_latch, m_testWinchForward, m_testWinchBackward);

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
    m_armUpButton.whenPressed(m_rollers.getArmsOut());
    m_armDownButton.whenPressed(m_rollers.getArmsIn());
    // m_intakeButton.whileHeld(m_rollers.getSetOn());
    // m_intakeButton.whenReleased(m_rollers.getSetOff());
    m_shooterDownButton.whenPressed(m_latch.getOpenLatch());
    m_hopperButton.whenPressed(m_latch.getCloseLatch());
    m_testLatchOpen.whenPressed(m_latch.getOpenLatch());
    m_testLatchClosed.whenPressed(m_latch.getCloseLatch());
    m_downButton.whenPressed(new SequentialCommandGroup(new AutoLatch(m_winch, m_latch), new AutoPull(m_winch)));
    m_shootButton.whenPressed(m_latch.getOpenLatch());
  }

  public Command getInitCommand() {
    m_winch.resetEncoder();
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
    m_drivetrain.highGear();
    m_winch.resetEncoder();
    return m_fullAuto;
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    m_drivetrain.setDefaultCommand(m_arcadeDrive);
    m_drivetrain.highGear();
    m_feedIn.schedule();

    return new TestClimb();
  }
}
