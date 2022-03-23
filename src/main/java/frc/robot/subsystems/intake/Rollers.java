// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Rollers extends SubsystemBase {
  private static final class Config {
    public static final int kRollerMotor = 0;
    public static final double kSpeed = 0.75;
  }

  private CANSparkMax m_motor = new CANSparkMax(Config.kRollerMotor, MotorType.kBrushless);

  /** Creates a new Rollers. */
  public Rollers() {

    m_motor.setIdleMode(IdleMode.kCoast);
  }

  public InstantCommand getSetOn() {
    return new InstantCommand(this::setOn, this);
  }

  public InstantCommand getSetOff() {
    return new InstantCommand(this::setOff, this);
  }

  public void setOn() {
    m_motor.set(Config.kSpeed);
  }

  public void setOff() {
    m_motor.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
