// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.slingshot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.slingshot.Bowl;
import frc.robot.subsystems.slingshot.Latch;

public class LatchSlingshot extends CommandBase {
  private static final class Config {
    public static final double kTicks = -215.0;
    public static final double kSpeed = 0.75;
  }

  private Bowl m_bowl;
  private Latch m_latch;
  private JoystickButton m_button;

  /** Creates a new Latch. */
  public LatchSlingshot(Bowl bowl, Latch latch, JoystickButton button) {
    m_bowl = bowl;
    m_latch = latch;
    m_button = button;

    addRequirements(bowl, latch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_button.get()) {
      m_bowl.set(-Config.kSpeed);
    } else {
      m_bowl.set(0.0);
    }
    SmartDashboard.putBoolean("Bowl/latching", true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_bowl.set(0.0);
    SmartDashboard.putBoolean("Bowl/latching", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_bowl.getEncoderValue() <= Config.kTicks;
  }
}
