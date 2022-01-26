/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Flywheel;

public class RampDownFlywheel extends CommandBase {
  /**
   * Creates a new RampDownFlywheel.
   */
  public static final class Config {

    public static final double kP = 2.0e-5; 
    // kP checks how close it is to value at small intervals

  }
    
  private final Flywheel m_flywheel;
  private double m_targetRPM;

  public RampDownFlywheel(Flywheel flywheel) {

      m_flywheel = flywheel;

      addRequirements(flywheel);
      // Use addRequirements() here to declare subsystem dependencies.
    }
  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = m_targetRPM - (-1 * m_flywheel.getRPM());
    m_flywheel.set(m_flywheel.getPercent() + (error * Config.kP));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_flywheel.setOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return Math.abs(m_flywheel.getRPM() - m_targetRPM) <= 100.0;

  }
}
