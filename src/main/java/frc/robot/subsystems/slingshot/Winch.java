// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.slingshot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Winch extends SubsystemBase {
  private static final class Config {
    public final static int kMotorPort = 7;
    public final static double kBottomLimit = 0.0; // TODO
    public final static double kSpeed = 0.3; // TODO
    public final static int kLimitSwtichChannel = 7;
  }

  private CANSparkMax m_motor = new CANSparkMax(Config.kMotorPort, MotorType.kBrushless);
  private RelativeEncoder m_encoder = m_motor.getEncoder();
  private DigitalInput m_limitSwitch = new DigitalInput(Config.kLimitSwtichChannel);

  /** Creates a new Winch. */
  public Winch() {
    m_motor.setIdleMode(IdleMode.kBrake);
    m_motor.setInverted(true);

    m_encoder.setPositionConversionFactor(1.0);
    SmartDashboard.putData("Winch/reset", getResetEncoder());
  }

  public void set(double speed) {
    m_motor.set(speed);
  }

  public void pull() {
    m_motor.set(Config.kSpeed);
  }

  public boolean isDown() {
    return m_limitSwitch.get();
  }

  public double getEncoderValue() {
    return m_encoder.getPosition();
  }

  public void resetEncoder() {
    m_encoder.setPosition(0.0);
  }

  public InstantCommand winchDown() {
    return new InstantCommand(() -> set(Config.kSpeed), this);
  }

  public InstantCommand stopwinch() {
    return new InstantCommand(() -> set(0.0), this);
  }

  public InstantCommand getResetEncoder() {
    return new InstantCommand(this::resetEncoder, this);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Winch/encoderValue", getEncoderValue());
    SmartDashboard.putBoolean("Winch/swtich", isDown());
  }
}
