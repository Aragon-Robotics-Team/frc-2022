// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climb;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {
  public static final class Config {
    public static final int kMotorPortLeft = 0;
    public static final int kMotorPortRight = 0;
    public static final double kSpeed = 0.25;
  }

  /** Creates a new Climb. */
  private WPI_TalonFX m_motorLeft = new WPI_TalonFX(Config.kMotorPortLeft);
  private WPI_TalonFX m_motorRight = new WPI_TalonFX(Config.kMotorPortRight);

  public Climb() {
  }

  public void resetEncoder() {
    m_motorLeft.setSelectedSensorPosition(0.0);
    m_motorRight.setSelectedSensorPosition(0.0);
  }

  public void set(double speed) {
    m_motorLeft.set(speed);
  }

  public InstantCommand rightArmUp() {
    return new InstantCommand(() -> m_motorRight.set(Config.kSpeed));
  }

  public InstantCommand rightarmdown() {
    return new InstantCommand(() -> m_motorRight.set(-Config.kSpeed));
  }

  public InstantCommand rightArmDown() {
    return new InstantCommand(() -> m_motorRight.set(0.0));
  }

  public InstantCommand leftArmUp() {
    return new InstantCommand(() -> m_motorLeft.set(Config.kSpeed));
  }

  public InstantCommand leftArmdown() {
    return new InstantCommand(() -> m_motorLeft.set(-Config.kSpeed));
  }

  public InstantCommand leftArmDown() {
    return new InstantCommand(() -> m_motorLeft.set(0.0));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climb/encoderLeft", m_motorLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Climb/encoderRight", m_motorRight.getSelectedSensorPosition());
  }
}
