/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {
  private Solenoid m_arm = new Solenoid(1, PneumaticsModuleType.CTREPCM, 5);

  /**
   * Creates a new Climb.
   */
  public Climb() {
    SmartDashboard.putData("Climb/up", getUp());
    SmartDashboard.putData("Climb/down", getDown());
  }

  public InstantCommand getUp() {
    return new InstantCommand(this::up, this);
  }

  public InstantCommand getDown() {
    return new InstantCommand(this::down, this);
  }

  public void down() {
    m_arm.set(false);
  }

  public void up() {
    m_arm.set(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
