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

public class AutoPull extends CommandBase {
  private Winch m_winch;
  private Latch m_latch;

  /**
   * Creates a new AutoPull.
   */
  public AutoPull(Winch winch, Latch latch) {
    m_winch = winch;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(winch, latch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_winch.pull();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_winch.set(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_winch.isDown();
  }
}
