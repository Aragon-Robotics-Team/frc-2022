// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.intake.Hopper;
import frc.robot.subsystems.intake.Rollers;

public class FeedIn extends CommandBase {
  private Rollers m_rollers;
  private Hopper m_hopper;
  private JoystickButton m_inButton;
  private JoystickButton m_outButton;

  /** Creates a new FeedIn. */
  public FeedIn(Rollers rollers, Hopper hopper, JoystickButton inButton, JoystickButton outButton) {
    m_rollers = rollers;
    m_hopper = hopper;
    m_inButton = inButton;
    m_outButton = outButton;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hopper, rollers);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_rollers.getState()) {
      if (m_inButton.get()) {
        m_rollers.setOn();
        m_hopper.setOn();
      } else if (m_outButton.get()) {
        m_rollers.setReverse();
        m_hopper.setReverse();
      } else {
        m_rollers.setOff();
        m_hopper.setOff();
      }
    } else {
      m_rollers.setOff();

      if (m_inButton.get()) {
        m_hopper.setOn();
      } else if (m_outButton.get()) {
        m_hopper.setReverse();
      } else {
        m_hopper.setOff();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_rollers.setOff();
    m_hopper.setOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
