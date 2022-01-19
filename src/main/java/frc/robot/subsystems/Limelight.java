/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;



public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
 public static final class LimelightConfigs {
   public static final double kPipeline = 0;
 }

 private NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
 private NetworkTableEntry m_tx = m_table.getEntry("tx");
 private NetworkTableEntry m_ty = m_table.getEntry("ty");
 private NetworkTableEntry m_tv = m_table.getEntry("tv");
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
