// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.slingshot.Bowl;

public class PullSlingshot extends CommandBase {
  private static final class Config {
    public static final double kTicks = 0;
    public static final double kMinSpeed = 0.25;
    public static final double kMaxSpeed = 0.25;
  }

  private Bowl m_bowl;
  private double m_speed;

  /** Creates a new PullSlingshot. */
  public PullSlingshot(Bowl bowl) {
    m_bowl = bowl;
    m_speed = Config.kMinSpeed;

    addRequirements(bowl);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_bowl.resetEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_bowl.set(m_speed);
    m_speed += (Config.kMaxSpeed - Config.kMinSpeed) / Config.kTicks;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_bowl.getEncoderValue() <= Config.kTicks;
  }
}
