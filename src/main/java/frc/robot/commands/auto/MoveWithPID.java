// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.MathUtil;
import frc.robot.subsystems.Drivetrain;

public class MoveWithPID extends CommandBase {
  private static final class Config {
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double kTolerance = 0.5 / 12.0;
  }

  private Drivetrain m_drivetrain;
  private PIDController m_pid;

  /** Creates a new MoveWithPID. */
  public MoveWithPID(Drivetrain drivetrain, double setpoint) {
    m_drivetrain = drivetrain;

    m_pid = new PIDController(Config.kP, Config.kI, Config.kD);
    m_pid.setSetpoint(setpoint);
    m_pid.setTolerance(Config.kTolerance);

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.resetEncoder();
    m_drivetrain.disableDeadband();
    m_pid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putData("PID/driveController", m_pid);
    double speed = MathUtil.clamp(m_pid.calculate(m_drivetrain.getDistance()), -1.0, 1.0);
    m_drivetrain.getDrive().tankDrive(speed, speed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.enableDeadband();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pid.atSetpoint();
  }
}
