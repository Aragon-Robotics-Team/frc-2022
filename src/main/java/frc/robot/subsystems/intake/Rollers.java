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
    public static final int kMotorPort = 0;
    public static final double kForwardSpeed = 0.25;
    public static final double kBackwardSpeed = -0.25;
  }

  private CANSparkMax m_motor = new CANSparkMax(Config.kMotorPort, MotorType.kBrushless);

  /** Creates a new Rollers. */
  public Rollers() {
    m_motor.setIdleMode(IdleMode.kCoast);
  }

  public void set(double v) {
    m_motor.set(v);
  }

  public InstantCommand rollFoward() {
    return new InstantCommand(() -> m_motor.set(Config.kForwardSpeed), this);
  }

  public InstantCommand rollBackward() {
    return new InstantCommand(() -> m_motor.set(Config.kBackwardSpeed), this);
  }

  public InstantCommand stopRollers() {
    return new InstantCommand(() -> m_motor.set(0.0), this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
