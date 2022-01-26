// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.driving.testing;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.PixyCamera;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

public class TestPixy extends CommandBase {
  private PixyCamera m_pixy;

  /** Creates a new TestPixy. */
  public TestPixy(PixyCamera pixy) {
    m_pixy = pixy;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Block biggestBlock = m_pixy.getBiggestBlock();

    boolean blockFound = biggestBlock != null;

    SmartDashboard.putBoolean("Pixy/Found Block", blockFound);
    SmartDashboard.putNumber("Pixy/fps", (double) m_pixy.getFPS());

    if (blockFound) {
      SmartDashboard.putNumber("Pixy/x", (double) biggestBlock.getX());
      SmartDashboard.putNumber("Pixy/y", (double) biggestBlock.getY());
      SmartDashboard.putNumber("Pixy/width", (double) biggestBlock.getWidth());
      SmartDashboard.putNumber("Pixy/height", (double) biggestBlock.getHeight());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
