// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.sensors.PixyCamera;
import frc.robot.subsystems.Drivetrain;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

public class TurnTowardCargo extends CommandBase {
  private static final class Config {
    public static final int kLeftBound = (int) (315.0 / 2.0 - 5.5);
    public static final int kRightBound = (int) (315.0 / 2.0 + 5.5);
    public static final double kSpeed = 0.45;
  }

  private Drivetrain m_drive;
  private PixyCamera m_pixy;
  private JoystickButton m_button;

  /** Creates a new TurnTowardCargo. */
  public TurnTowardCargo(Drivetrain drive, PixyCamera pixy, JoystickButton button) {
    m_drive = drive;
    m_pixy = pixy;
    m_button = button;

    addRequirements(drive, pixy);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.setIdleMode(NeutralMode.Brake);
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
    logBlockInfo(block);

    if (m_button.get()) {
      double blockX = block.getX();

      if (blockX < Config.kLeftBound) {
        m_drive.getDrive().tankDrive(-Config.kSpeed, Config.kSpeed);
      } else if (blockX > Config.kRightBound) {
        m_drive.getDrive().tankDrive(Config.kSpeed, -Config.kSpeed);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void logBlockInfo(Block biggestBlock) {
    SmartDashboard.putBoolean("Pixy/found", true);
    SmartDashboard.putNumber("Pixy/x", (double) biggestBlock.getX());
    SmartDashboard.putNumber("Pixy/y", (double) biggestBlock.getY());
    SmartDashboard.putNumber("Pixy/width", (double) biggestBlock.getWidth());
    SmartDashboard.putNumber("Pixy/height", (double) biggestBlock.getHeight());
  }
}
