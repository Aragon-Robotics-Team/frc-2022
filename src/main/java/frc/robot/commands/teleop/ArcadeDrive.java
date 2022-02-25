// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  public static final class Config {
    public static final int kLeftStickY = 1;
    public static final int kRightStickX = 2;

    public static final double kSpeedMult = 1.0;
    public static final double kTurnMult = 1.0;
  }

  private Drivetrain m_drivetrain;
  private Joystick m_joystick;

  /** Creates a new ArcadeDrive. */
  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_joystick.getRawAxis(Config.kLeftStickY) * Config.kSpeedMult;
    double turn = m_joystick.getRawAxis(Config.kRightStickX) * Config.kTurnMult;

    m_drivetrain.getDrive().arcadeDrive(speed, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.getDrive().stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
