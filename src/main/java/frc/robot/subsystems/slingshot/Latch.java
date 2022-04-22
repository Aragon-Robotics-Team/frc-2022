// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.slingshot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Latch extends SubsystemBase {
  private static final class Config {
    public static final int kChannel = 9;
  }

  // private Solenoid m_solenoid = new Solenoid(1, PneumaticsModuleType.CTREPCM,
  // Config.kChannel);
  private Servo m_servo = new Servo(Config.kChannel);
  private boolean m_open = false;

  /** Creates a new Latch. */
  public Latch() {
  }

  public InstantCommand getOpenLatch() {
    return new InstantCommand(this::openLatch);
  }

  public InstantCommand getCloseLatch() {
    return new InstantCommand(this::closeLatch);
  }

  public void openLatch() {
    m_servo.set(0.0);
    m_open = true;
  }

  public void closeLatch() {
    m_servo.set(0.7);
    m_open = false;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Latch/open", m_open);
    SmartDashboard.putNumber("Shooter/servo", m_servo.get());
  }
}
