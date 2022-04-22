// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.slingshot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.intake.Rollers;
import frc.robot.subsystems.slingshot.Latch;
import frc.robot.subsystems.slingshot.Winch;

public class TestWinch extends CommandBase {
  private Winch m_winch;
  private Latch m_latch;
  private JoystickButton m_forward;
  private JoystickButton m_back;
  private boolean m_stop;

  /** Creates a new TestWinch. */
  public TestWinch(Winch winch, Latch latch, JoystickButton forward, JoystickButton backward) {
    m_winch = winch;
    m_latch = latch;
    m_forward = forward;
    m_back = backward;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(winch, latch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stop = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = 0.3;
    if (m_forward.get()) {
      if (m_winch.isUp()) {
        m_winch.set(0.0);
        m_winch.resetEncoder();
      } else {
        m_winch.set(speed);
      }
    } else if (m_back.get()) {
      m_winch.set(-speed);
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
