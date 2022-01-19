/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Funnel extends SubsystemBase {
  public static final class Config {
    // Values yet to be set, must be changed once available
    public static final int kLeftMotorPort = 1;
    public static final int kRightMotorPort = 2;

    // Value not tested
    public static final double kFunnelSpeed = 0.5;
  }
  
  private final CANSparkMax m_leftMotor = new CANSparkMax(Config.kLeftMotorPort, MotorType.kBrushless);
  private final CANSparkMax m_rightMotor = new CANSparkMax(Config.kRightMotorPort, MotorType.kBrushless);
  
  /**
   * Creates a new Funnel.
   */
  public Funnel() {
    m_leftMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
