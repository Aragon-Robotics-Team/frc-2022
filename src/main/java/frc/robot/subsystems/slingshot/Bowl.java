// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.slingshot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bowl extends SubsystemBase {
  private static final class Config {
    public static final int kRightMotorPort = 8;
    public static final int kLeftMotorPort = 9;
  }

  private CANSparkMax m_motorRight = new CANSparkMax(Config.kRightMotorPort, MotorType.kBrushless);
  private CANSparkMax m_motorLeft = new CANSparkMax(Config.kLeftMotorPort, MotorType.kBrushless);
  private RelativeEncoder m_encoder = m_motorRight.getEncoder();

  /** Creates a new Bowl. */
  public Bowl() {
    m_motorRight.setIdleMode(IdleMode.kBrake);
    m_motorLeft.setIdleMode(IdleMode.kBrake);

    m_encoder.setPositionConversionFactor(1.0);
    SmartDashboard.putData("Bowl/reset", getResetEncoder());
  }

  public void set(double speed) {
    m_motorRight.set(speed);
    m_motorLeft.set(speed);
  }

  public double getEncoderValue() {
    return m_encoder.getPosition();
  }

  public void resetEncoder() {
    m_encoder.setPosition(0.0);
  }

  public InstantCommand bowlDown() {
    return new InstantCommand(() -> set(1.0), this);
  }

  public InstantCommand bowlUp() {
    return new InstantCommand(() -> set(-1.0), this);
  }

  public InstantCommand stopBowl() {
    return new InstantCommand(() -> set(0.0), this);
  }

  public InstantCommand getResetEncoder() {
    return new InstantCommand(this::resetEncoder, this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Bowl/encoderValue", getEncoderValue());
  }
}
