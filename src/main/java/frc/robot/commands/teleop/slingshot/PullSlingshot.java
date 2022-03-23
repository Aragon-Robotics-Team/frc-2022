// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.slingshot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.slingshot.Bowl;

public class PullSlingshot extends CommandBase {
  private static final class Config {
    public static final double kTicks = 0.0;
    public static final double kMaxSpeed = 0.75;
  }

  private Bowl m_bowl;
  private double m_speed;
  private JoystickButton m_button;

  /** Creates a new PullSlingshot. */
  public PullSlingshot(Bowl bowl, JoystickButton button) {
    m_bowl = bowl;
    m_button = button;

    addRequirements(bowl);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_button.get()) {
      m_bowl.set(Config.kMaxSpeed);
    } else {
      m_bowl.set(0.0);
    }
    SmartDashboard.putBoolean("Bowl/pulling", true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_bowl.set(0.0);
    SmartDashboard.putBoolean("Bowl/pulling", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_bowl.getEncoderValue() >= Config.kTicks;
    // return false;

  }
}
