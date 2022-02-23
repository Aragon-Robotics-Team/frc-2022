// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyroscope extends SubsystemBase {
  private ADXRS450_Gyro m_gyro;

  /** Creates a new Gyroscope. */
  public Gyroscope() {
    m_gyro = new ADXRS450_Gyro();
  }

  public void reset() {
    m_gyro.reset();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Gyro/angle", m_gyro.getAngle());
  }
}
