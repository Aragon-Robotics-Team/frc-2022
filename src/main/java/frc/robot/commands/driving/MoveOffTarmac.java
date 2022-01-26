// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.controller.PIDController;

public class MoveOffTarmac extends CommandBase {
  private static final class Config {
    // test later
    public static final double kSetpoint = 0.0;

    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }

  /** Creates a new MoveOffTarmac. */
  private Drivetrain m_drivetrain;
  private final PIDController m_pid = new PIDController(Config.kP, Config.kI, Config.kD);


  public MoveOffTarmac(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;

    addRequirements(drivetrain);

  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //add a shooting cmd here? worth thinking about
    m_drivetrain.resetEncoder(); 

    m_pid.setSetpoint(Config.kSetpoint);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_pid.calculate(m_drivetrain.position());
    //woah tank drive
    //makes the pid robot move :O
    m_drivetrain.getDrive().tankDrive(speed, speed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.getDrive().stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pid.atSetpoint();
  }
}
