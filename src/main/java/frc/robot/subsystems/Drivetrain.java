// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  public static final class Config {
    public static final int kRightMotorPrimary = 0;
    public static final int kRightMotorSecondary = 0;
    public static final int kLeftMotorPrimary = 0;
    public static final int kLeftMotorSecondary = 0;
  }

  private WPI_TalonFX m_rightMotorPrimary = new WPI_TalonFX(Config.kRightMotorPrimary);
  private WPI_TalonFX m_rightMotorSecondary = new WPI_TalonFX(Config.kRightMotorSecondary);
  private WPI_TalonFX m_leftMotorPrimary = new WPI_TalonFX(Config.kLeftMotorPrimary);
  private WPI_TalonFX m_leftMotorSecondary = new WPI_TalonFX(Config.kLeftMotorSecondary);

  private DifferentialDrive m_drive = new DifferentialDrive(m_rightMotorPrimary, m_leftMotorPrimary);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_rightMotorSecondary.follow(m_rightMotorPrimary);
    m_leftMotorSecondary.follow(m_leftMotorPrimary);

    m_leftMotorPrimary.setInverted(true);
    m_leftMotorSecondary.setInverted(true);
  }

  public DifferentialDrive getDrive() {
    return m_drive;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
