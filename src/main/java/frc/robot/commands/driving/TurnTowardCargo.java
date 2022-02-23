// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.driving;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.sensors.PixyCamera;
import frc.robot.subsystems.Drivetrain;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

public class TurnTowardCargo extends CommandBase {
  private static final class Config {
    public static final double kCenterBound = 10.0;
    public static final double kCenter = 315.0 / 2.0;
    public static final double kSpeed = 0.45;

    public static final double kP = 0.01;
    public static final double kI = 0.;
    public static final double kD = 0.0;
  }

  private Drivetrain m_drive;
  private PixyCamera m_pixy;
  private JoystickButton m_button;
  private PIDController m_pid;

  /** Creates a new TurnTowardCargo. */
  public TurnTowardCargo(Drivetrain drive, PixyCamera pixy, JoystickButton button) {
    m_drive = drive;
    m_pixy = pixy;
    m_button = button;

    addRequirements(drive, pixy);

    m_pid = new PIDController(Config.kP, Config.kI, Config.kD);
    m_pid.setTolerance(Config.kCenterBound);
    m_pid.setSetpoint(Config.kCenter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.setIdleMode(NeutralMode.Brake);
    SmartDashboard.putBoolean("Pixy/turning", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Block block = m_pixy.getBiggestBlock();
    if (block == null) {
      m_drive.getDrive().tankDrive(0.0, 0.0);
      SmartDashboard.putBoolean("Pixy/found", false);
      return;
    }
    m_pixy.logBlockInfo(block);

    if (m_button.get()) {
      double blockX = block.getX();
      double speed = m_pid.calculate(blockX);
      SmartDashboard.putNumber("Pixy/pid", speed);
      speed = MathUtil.clamp(speed, -Config.kSpeed, Config.kSpeed);

      m_drive.getDrive().tankDrive(-speed, speed);
    } else {
      m_drive.getDrive().tankDrive(0.0, 0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.getDrive().stopMotor();
    SmartDashboard.putBoolean("Pixy/turning", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return m_pid.atSetpoint();
    return false;
  }
}
