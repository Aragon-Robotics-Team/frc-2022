/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.teleop.slingshot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.slingshot.Winch;
import frc.robot.subsystems.slingshot.Latch;

/**
 * OBSOLETE
 */
public class AutoLatch extends CommandBase {
  private static final class Config {
    public static final double kTicks = -215.0;
    public static final double kSpeed = 1.0;
  }

  private Winch m_bowl;
  private Latch m_latch;

  /**
   * Creates a new AutoLatch.
   */
  public AutoLatch(Winch bowl, Latch latch) {
    m_bowl = bowl;
    m_latch = latch;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(bowl, latch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_latch.closeLatch();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_bowl.set(-Config.kSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_bowl.set(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_bowl.getEncoderValue() <= Config.kTicks;
  }
}
