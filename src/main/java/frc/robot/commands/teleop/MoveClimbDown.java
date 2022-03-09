// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;


public class MoveClimbDown extends CommandBase {
  private static final class Config {
    public static final double kSpeed = 0.0;
    public static final double kTicks = 0.0; 
   }

  private Climb m_climb;
  private double m_speed;

  /** Creates a new TurnMotor. */
  public MoveClimbDown(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
      m_climb = climb;
      m_speed = Config.kSpeed;

      addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.set(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.set(0.0);
  }

  // Returns true when the command should end.

  //Testing required to find the ticks at which the motor should stop
  @Override
  public boolean isFinished() { 
    return m_climb.getEncoderValue() <= Config.kTicks;
  }
}