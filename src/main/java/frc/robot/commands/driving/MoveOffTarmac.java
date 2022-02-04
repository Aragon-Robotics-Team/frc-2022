// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveOffTarmac extends CommandBase {
  private static final class Config {
    // test later
    public static final double kSetpoint = 5;

    public static final double kP = 0.2;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double kTicksPerFeet = 5300;
  }

  /** Creates a new MoveOffTarmac. */
  private Drivetrain m_drivetrain;
  private final PIDController m_pid = new PIDController(Config.kP, Config.kI, Config.kD);

  public MoveOffTarmac(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;

    addRequirements(drivetrain);

    SmartDashboard.putNumber("PID/speed", 0.0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // add a shooting cmd here? worth thinking about
    m_drivetrain.resetEncoder();

    m_pid.setSetpoint(Config.kSetpoint);
    m_pid.setTolerance(1.0 / 12.0);

    m_drivetrain.setIdleMode(NeutralMode.Brake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = -m_pid.calculate(m_drivetrain.position() / Config.kTicksPerFeet);
    speed = MathUtil.clamp(speed, -0.75, 0.75);

    SmartDashboard.putNumber("PID/speed", speed);
    // woah tank drive
    // makes the pid robot move :O
    m_drivetrain.getDrive().tankDrive(speed, speed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.getDrive().stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pid.atSetpoint();
  }
}
