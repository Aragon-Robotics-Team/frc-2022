/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends SubsystemBase {
  /**
   * Creates a new Flywheel.
   */

   public static final class Config {

    public static final int kFlyMotorPortMaster = 1;
    public static final int kSlaveMotorPortMaster = 2;

    public static final double kGearRatio = 1.0;
    public static final double kEncoderRes = 4096.0;

    public static final double kTargetRPM = 4000.0;

   }

   private final TalonSRX m_flyMotorMaster = new TalonSRX(Config.kFlyMotorPortMaster);
   private final TalonSRX m_flyMotorSlave = new TalonSRX(Config.kSlaveMotorPortMaster);


  public Flywheel() {

    m_flyMotorMaster.setInverted(false);
    m_flyMotorSlave.setInverted(true);

    m_flyMotorMaster.setNeutralMode(NeutralMode.Coast);
    m_flyMotorSlave.setNeutralMode(NeutralMode.Coast);

    m_flyMotorSlave.follow(m_flyMotorMaster);

  }

  public void setOff() {
    
    //set(0.0);   Issue to look into later

  }

  public double getRPM() {

    return -1.0 * m_flyMotorMaster.getSelectedSensorVelocity() * Config.kGearRatio / Config.kEncoderRes * 60;

  }

  
  public double getPercent() {

    return m_flyMotorMaster.getMotorOutputPercent();

  } //Finds out value of individual percent as RPM can be increased using pecent values

  public void set(double v) {

    m_flyMotorMaster.set(TalonSRXControlMode.PercentOutput, v);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    super.periodic();

    //Add rest of code later
  }
}
