// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Rollers extends SubsystemBase {
  private static final class Config {
    public static final int kRollerMotor = 1;
    public static final double kSpeed = 0.6;
    public static final int kArmChannel = 0;
  }

  private CANSparkMax m_motor = new CANSparkMax(Config.kRollerMotor, MotorType.kBrushless);
  private Solenoid m_arms = new Solenoid(1, PneumaticsModuleType.CTREPCM, Config.kArmChannel);

  /** Creates a new Rollers. */
  public Rollers() {
    m_motor.setInverted(false);

    m_motor.setIdleMode(IdleMode.kCoast);
  }

  public InstantCommand getSetOn() {
    return new InstantCommand(this::setOn, this);
  }

  public InstantCommand getSetOff() {
    return new InstantCommand(this::setOff, this);
  }

  public void setOn() {
    m_motor.set(-Config.kSpeed);
  }

  public boolean getState() {
    return m_arms.get();
  }

  public void setReverse() {
    m_motor.set(-Config.kSpeed);
  }

  public void setOff() {
    m_motor.set(0.0);
  }

  public void armsIn() {
    m_arms.set(false);
  }

  public void armsOut() {
    m_arms.set(true);
  }

  public InstantCommand getArmsIn() {
    return new InstantCommand(this::armsIn);
  }

  public InstantCommand getArmsOut() {
    return new InstantCommand(this::armsOut);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
