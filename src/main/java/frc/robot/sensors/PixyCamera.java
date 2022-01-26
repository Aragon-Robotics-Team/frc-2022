// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

/** Add your docs here. */
public class PixyCamera extends SubsystemBase {

    private final Pixy2 pixy;
    private static final int kPixySSPort = 0;

    private boolean blockFound;
    private int x, y, width, height;
    private int fps;


    public PixyCamera() {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.setLamp( (byte) 1, (byte) 1);
        pixy.setLED(0, 255, 255);
        // Creates a new Pixy2 camera using SPILink
		pixy.init(kPixySSPort); // Initializes the camera and prepares to send/receive data
		pixy.setLamp((byte) 1, (byte) 1); // Turns the LEDs on
        pixy.setLED(255, 255, 255); // Sets the RGB LED to purple
    }


    private void logPixyError(int errorCode) {
        System.out.println("[PIXY ERROR] " + errorCode);
    }

    public Block getBiggestBlock() {
        // Gets the number of "blocks", identified targets, that match signature 1 on the Pixy2,
		// does not wait for new data if none is available,
		// and limits the number of returned blocks to 25, for a slight increase in efficiency
		int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 25);
		System.out.println("Found " + blockCount + " blocks!"); // Reports number of blocks found
		if (blockCount <= 0) {
            System.out.println("No blocks were found.");
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


    public double getDistance() {

        final double kFocal = (106 * 20) / 9.25;

        double distance = 9.25 * kFocal / getBiggestBlock().getWidth();

        return distance;



    }

    public byte getFPS() {
        return pixy.getFPS();
    }

    public void moveToCargo() {


    }

    public enum PixyCam {

        
    }



}

