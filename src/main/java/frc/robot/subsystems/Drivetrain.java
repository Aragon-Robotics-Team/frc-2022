// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {
  private static final class Config {
    // placeholders
    // actually channels (victors) / device nunmbers (talons)
    public static final int kLeftMotorSlave = 0;
    public static final int kRightMotorSlave = 1;
    public static final int kLeftMotorMaster = 2;
    public static final int kRightMotorMaster = 3;

  }
  /*

  talons = masters
  victors = slaves

  */

  // private CANSparkMax m_leftMotorSlave = new CANSparkMax(Config.kLeftMotorSlave, MotorType.kBrushless);
  // private CANSparkMax m_rightMotorSlave = new CANSparkMax(Config.kRightMotorSlave, MotorType.kBrushless);
  // private CANSparkMax m_leftMotorMaster = new CANSparkMax(Config.kLeftMotorMaster, MotorType.kBrushless);
  // private CANSparkMax m_rightMotorMaster = new CANSparkMax(Config.kRightMotorMaster, MotorType.kBrushless);

  private VictorSPX m_leftMotorSlave = new VictorSPX(Config.kLeftMotorSlave);
  private VictorSPX m_rightMotorSlave = new VictorSPX(Config.kRightMotorSlave);
  private WPI_TalonSRX m_leftMotorMaster = new WPI_TalonSRX(Config.kLeftMotorMaster);
  private WPI_TalonSRX m_rightMotorMaster = new WPI_TalonSRX(Config.kRightMotorMaster);

  // make encoders for talon motors

  //encoders: CTRE magnetic encoders :)

  // private CANEncoder m_encRight = m_rightMotorMaster.getCANEncoder();
  // private CANEncoder m_encLeft = m_leftMotorMaster.getCANEnoder();

  private DifferentialDrive m_drive = new DifferentialDrive(m_leftMotorMaster, m_rightMotorMaster);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // Inverts masters
    m_leftMotorMaster.setInverted(true);
    m_rightMotorMaster.setInverted(true);

    // Sets following
    
    m_leftMotorSlave.follow(m_leftMotorMaster);
    m_rightMotorSlave.follow(m_rightMotorMaster);

    // m_encLeft.setPositionConversionFactor(1.0);
    // m_encRight.setPositionConversionFactor(1.0);
  }

  public void stopMotors() {
    m_leftMotorMaster.stopMotor();
    m_rightMotorMaster.stopMotor();
  }

  public void resetEncoder() {
    // m_encRight.setPosition(0);
    // m_encLeft.setPosition(0);
    m_leftMotorMaster.setSelectedSensorPosition(0.0);
    m_rightMotorMaster.setSelectedSensorPosition(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public DifferentialDrive getDrive() {

    return m_drive;

  }
}
