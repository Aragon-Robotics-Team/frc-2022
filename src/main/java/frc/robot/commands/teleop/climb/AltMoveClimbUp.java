// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package main.java.frc.robot.commands.teleop.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.intake.Arm;

public class AltMoveClimbUp extends InstantCommand {
  private final AltClimb m_altclimb;

  public AltClimbClimbUp(AltClimb altClimb) {
    m_altclimb = altClimb;

    addRequirements(m_altClimb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_altclimb.armUp();
  }
}
