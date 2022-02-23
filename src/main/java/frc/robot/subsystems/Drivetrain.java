// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static final class Config {
    public static final int kLeftMotorSlave = 8;
    public static final int kRightMotorSlave = 7;
    public static final int kLeftMotorMaster = 6;
    public static final int kRightMotorMaster = 3;
  }

  private WPI_VictorSPX m_leftMotorSlave = new WPI_VictorSPX(Config.kLeftMotorSlave);
  private WPI_VictorSPX m_rightMotorSlave = new WPI_VictorSPX(Config.kRightMotorSlave);
  private WPI_TalonSRX m_leftMotorMaster = new WPI_TalonSRX(Config.kLeftMotorMaster);
  private WPI_TalonSRX m_rightMotorMaster = new WPI_TalonSRX(Config.kRightMotorMaster);

  private DifferentialDrive m_drive = new DifferentialDrive(m_leftMotorMaster, m_rightMotorMaster);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_leftMotorSlave.setInverted(false);
    m_rightMotorSlave.setInverted(false);

    // Sets following
    m_leftMotorSlave.follow(m_leftMotorMaster);
    m_rightMotorSlave.follow(m_rightMotorMaster);
  }

  public void stopMotors() {
    m_leftMotorMaster.stopMotor();
    m_rightMotorMaster.stopMotor();
  }

  public void resetEncoder() {
    m_leftMotorMaster.setSelectedSensorPosition(0.0);
    m_rightMotorMaster.setSelectedSensorPosition(0.0);
  }

  public double position() {
    return -m_leftMotorMaster.getSelectedSensorPosition();
  }

  public void setIdleMode(NeutralMode n) {
    m_rightMotorMaster.setNeutralMode(n);
    m_leftMotorMaster.setNeutralMode(n);
    m_rightMotorSlave.setNeutralMode(n);
    m_leftMotorSlave.setNeutralMode(n);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Drivetrain/encoder", m_rightMotorMaster.getSelectedSensorPosition());
  }

  public DifferentialDrive getDrive() {
    return m_drive;
  }
}
