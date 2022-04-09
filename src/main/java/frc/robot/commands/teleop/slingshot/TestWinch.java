// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.slingshot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.intake.Rollers;
import frc.robot.subsystems.slingshot.Winch;

public class TestWinch extends CommandBase {
  private Winch m_winch;
  private Rollers m_rollers;
  private JoystickButton m_button;
  private boolean m_stop;

  /** Creates a new TestWinch. */
  public TestWinch(Winch winch, Rollers rollers, JoystickButton button) {
    m_winch = winch;
    m_button = button;
    m_rollers = rollers;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(winch, rollers);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stop = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_winch.isDown()) {
      if (m_button.get()) {
        m_winch.set(0.3);
      } else {
        m_winch.set(0.0);
      }
    } else {
      m_winch.set(0.0);
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
}
