// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TimedAutoLine extends CommandBase {
  private static final class Config {
    public static final double kTime = 1.25;
    public static final double kSpeed = 0.3;
  }

  private Drivetrain m_drivetrain;
  private double m_startTime;

  /** Creates a new TimedAutoLine. */
  public TimedAutoLine(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.setIdleMode(NeutralMode.Brake);
    m_startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.getDrive().tankDrive(Config.kSpeed, Config.kSpeed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.getDrive().stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() - m_startTime >= Config.kTime;
  }
}
