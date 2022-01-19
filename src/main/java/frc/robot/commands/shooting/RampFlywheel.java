/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Flywheel;

public class RampFlywheel extends SubsystemBase {
  /**
   * Creates a new RampFlywheel.
   */

   public static final class Config {

    public static final double kP = 2.0e-5;
    // kP checks how close it is to value at small intervals
   }

   private final Flywheel m_flywheel;
   private double m_targetRPM;

  public RampFlywheel(Flywheel flywheel) {

    m_flywheel = flywheel;

    //addRequirements(flywheel);  Look into it later

  }

  @Override
  public void initialize() {

    m_targetRPM = SmartDashboard.getNumber("Target RPM", Flywheel.Config.kTargetRPM);
  }

  @Override
  public void execute() {

    double error = m_targetRPM - m_flywheel.getRPM();
    m_flywheel.set(m_flywheel.getPercent() + (error * Config.kP));
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {

    return Math.abs(m_flywheel.getRPM() - m_targetRPM) <= 25.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
