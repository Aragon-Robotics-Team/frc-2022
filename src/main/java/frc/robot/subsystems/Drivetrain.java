// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static final class Config {
    public static final int kRightMotorPrimary = 2;
    public static final int kRightMotorSecondary = 1;
    public static final int kLeftMotorPrimary = 4;
    public static final int kLeftMotorSecondary = 3;

    public static final double kWheelDiameter = 0.5;
    public static final double kTicksPerRotation = 2048.0;
    public static final double kGearRatio = 9.56;

    public static final int kPCMId = 1;

    public static final int kLeftGearLow = 6;
    public static final int kLeftGearHigh = 1;
  }

  private WPI_TalonFX m_rightMotorPrimary = new WPI_TalonFX(Config.kRightMotorPrimary);
  private WPI_TalonFX m_rightMotorSecondary = new WPI_TalonFX(Config.kRightMotorSecondary);
  private WPI_TalonFX m_leftMotorPrimary = new WPI_TalonFX(Config.kLeftMotorPrimary);
  private WPI_TalonFX m_leftMotorSecondary = new WPI_TalonFX(Config.kLeftMotorSecondary);

  private DifferentialDrive m_drive = new DifferentialDrive(m_rightMotorPrimary, m_leftMotorPrimary);

  private DoubleSolenoid m_dogShifters = new DoubleSolenoid(Config.kPCMId, PneumaticsModuleType.CTREPCM,
      Config.kLeftGearHigh,
      Config.kLeftGearLow);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_rightMotorSecondary.follow(m_rightMotorPrimary);
    m_leftMotorSecondary.follow(m_leftMotorPrimary);

    m_rightMotorPrimary.setInverted(true);
    m_rightMotorSecondary.setInverted(true);

    SmartDashboard.putData("Reset Encoder", new InstantCommand(this::resetEncoder, this));
    SmartDashboard.putData("High Gear", getHighGear());
    SmartDashboard.putData("Low Gear", getLowGear());
  }

  public DifferentialDrive getDrive() {
    return m_drive;
  }

  /**
   * @return Distance travelled in feet.
   */
  public double getDistance() {
    return (m_rightMotorPrimary.getSelectedSensorPosition() * Config.kWheelDiameter * Math.PI)
        / (Config.kTicksPerRotation * Config.kGearRatio);
  }

  public void resetEncoder() {
    m_rightMotorPrimary.setSelectedSensorPosition(0.0);
  }

  public void enableDeadband() {
    m_drive.setDeadband(DifferentialDrive.kDefaultDeadband);
  }

  public void disableDeadband() {
    m_drive.setDeadband(0.0);
  }

  public void setIdleMode(NeutralMode n) {
    m_rightMotorPrimary.setNeutralMode(n);
    m_rightMotorSecondary.setNeutralMode(n);
    m_leftMotorPrimary.setNeutralMode(n);
    m_leftMotorSecondary.setNeutralMode(n);
  }

  public void highGear() {
    m_dogShifters.set(Value.kForward);
  }

  public void lowGear() {
    m_dogShifters.set(Value.kReverse);
  }

  public InstantCommand getHighGear() {
    return new InstantCommand(this::highGear, this);
  }

  public InstantCommand getLowGear() {
    return new InstantCommand(this::lowGear, this);
  }

  @Override
  public void periodic() {
    SmartDashboard.putData("Drivetrain/DifferentialDrive", m_drive);
    SmartDashboard.putNumber("Drivetrain/encoder", m_rightMotorPrimary.getSelectedSensorPosition());
    SmartDashboard.putNumber("Drivetrain/distance", getDistance());
  }
}
