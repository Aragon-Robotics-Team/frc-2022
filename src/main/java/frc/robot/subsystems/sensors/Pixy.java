// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.I2CLink;

public class Pixy extends SubsystemBase {
  /** Creates a new Pixy. */

  private final Pixy2 pixy;

  public Pixy() {
    pixy = Pixy2.createInstance(new I2CLink());
    pixy.init(); // Initializes the camera and prepares to send/receive data
    pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
    pixy.setLED(255, 255, 255);
  }

  public Block getBiggestBlock() {
    // Gets the number of "blocks", identified targets, that match signature 1 on
    // the Pixy2,
    // does not wait for new data if none is available,
    // and limits the number of returned blocks to 25, for a slight increase in
    // efficiency
    int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 25);
    if (blockCount <= 0) {
      return null; // If blocks were not found, stop processing
    }
    ArrayList<Block> blocks = pixy.getCCC().getBlockCache(); // Gets a list of all blocks found by the Pixy2
    Block largestBlock = null;
    for (Block block : blocks) { // Loops through all blocks and finds the widest one
      if (largestBlock == null) {
        largestBlock = block;
      } else if (block.getWidth() > largestBlock.getWidth()) {
        largestBlock = block;
      }

    }
    return largestBlock;
  }

  public byte getFPS() {
    return pixy.getFPS();
  }

  public void logBlockInfo(Block block) {
    SmartDashboard.putBoolean("Pixy/found", true);
    SmartDashboard.putNumber("Pixy/x", (double) block.getX());
    SmartDashboard.putNumber("Pixy/y", (double) block.getY());
    SmartDashboard.putNumber("Pixy/width", (double) block.getWidth());
    SmartDashboard.putNumber("Pixy/height", (double) block.getHeight());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
