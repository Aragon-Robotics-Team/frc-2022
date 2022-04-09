// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {
  public static final class Config {
    public static final int kHopperPort = 10;
    public static final double kHopperSpeed = 0.8;
  }

  private WPI_TalonSRX m_motor = new WPI_TalonSRX(Config.kHopperPort);

  /** Creates a new Hopper. */
  public Hopper() {
    m_motor.setInverted(true);
  }

  public InstantCommand HopperOn() {
    return new InstantCommand(this::setOn, this);
  }

  public InstantCommand HopperOff() {
    return new InstantCommand(this::setOff, this);
  }

  public void setOn() {
    m_motor.set(Config.kHopperSpeed);
  }

  public void setReverse() {
    m_motor.set(-Config.kHopperSpeed);
  }

  public void setOff() {
    m_motor.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
