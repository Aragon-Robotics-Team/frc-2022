// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Latch extends SubsystemBase {
  private Servo m_servo = new Servo(0);
  private double m_value = 0.0;

  /** Creates a new Latch. */
  public Latch() {
    SmartDashboard.putNumber("Latch/value", 0);
  }

  @Override
  public void periodic() {
    double sdValue = SmartDashboard.getNumber("Latch/value", 0);
    if (sdValue != m_value) {
      m_value = sdValue;
      m_servo.set(m_value);
    }
  }
}
