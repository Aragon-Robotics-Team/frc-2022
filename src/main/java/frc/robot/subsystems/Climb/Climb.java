// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climb extends SubsystemBase {
  public static final class Config {
    public static final int kMotorPort = 0;
  }

  /** Creates a new Climb. */

  private CANSparkMax m_motor = new CANSparkMax(Config.kMotorPort, MotorType.kBrushless);
  private CANEncoder m_encoder = m_motor.getEncoder();

  public Climb() {
    m_encoder.setPositionConversionFactor(1.0);
  }


  public double getEncoderValue() {
    return m_encoder.getPosition();
  }

  public void resetEncoder() {
    m_encoder.setPosition(0.0);
  }

  public void set(double speed) {
    m_motor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climb/encoderValue", getEncoderValue());

  }
  }

