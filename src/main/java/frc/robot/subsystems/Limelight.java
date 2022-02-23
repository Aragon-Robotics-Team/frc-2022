/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  public static final class Config {
    public static final double kPipeline = 4;
    public static final double kTargetHeight = 3.28;
    public static final double kMountingHeight = 9.0 / 12.0;
    public static final double kMountingAngle = 0;
  }

  private NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry m_tx = m_table.getEntry("tx");
  private NetworkTableEntry m_ty = m_table.getEntry("ty");
  private NetworkTableEntry m_tv = m_table.getEntry("tv");
  private NetworkTableEntry m_pipeline = m_table.getEntry("getpipe");
  private NetworkTableEntry m_setPipeline = m_table.getEntry("pipeline");
  private NetworkTableEntry m_ledMode = m_table.getEntry("ledMode");

  public Limelight() {
    SmartDashboard.putBoolean("Limelight/led", true);
  }

  public double getTx() {
    return m_tx.getDouble(0);

  }

  public double getTy() {
    return m_ty.getDouble(0);

  }

  public double getTv() {
    return m_tv.getDouble(0);
  }

  public double getPipeline() {
    return m_pipeline.getDouble(0.0);
  }

  public void setLamp(boolean on) {
    if (on) {
      m_ledMode.setNumber(3.0);
    } else {
      m_ledMode.setNumber(1.0);
    }
  }

  public double findDistance() {
    if (getTv() == 1.0) {
      return (Config.kTargetHeight - Config.kMountingHeight)
          / Math.tan(Math.toRadians(Config.kMountingAngle + getTy()));
    } else {
      return 0.0;
    }
  }

  @Override
  public void periodic() {
    setLamp(SmartDashboard.getBoolean("Limelight/led", true));
    // This method will be called once per scheduler run
    // Take lemonlamp values and put them onto SmartDashboard ()
    SmartDashboard.putNumber("Limelight/tv", getTv());
    SmartDashboard.putNumber("Limelight/tx", getTx());
    SmartDashboard.putNumber("Limelight/ty", getTy());
    double pipeline = getPipeline();
    if (pipeline != Config.kPipeline) {
      m_setPipeline.setNumber(Config.kPipeline);
    }
    SmartDashboard.putNumber("Limelight/pipeline", pipeline);
    SmartDashboard.putNumber("Limelight/distance", findDistance());
  }
}
