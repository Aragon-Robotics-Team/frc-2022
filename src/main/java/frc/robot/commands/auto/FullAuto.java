// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.slingshot.AutoDelay;
import frc.robot.commands.teleop.slingshot.AutoPull;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.slingshot.Latch;
import frc.robot.subsystems.slingshot.Winch;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FullAuto extends SequentialCommandGroup {
  /** Creates a new FullAuto. */
  public FullAuto(Winch bowl, Latch latch, Drivetrain drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(latch.getCloseLatch(), new AutoPull(bowl, latch), latch.getOpenLatch(), new AutoDelay(0.5),
        new TimedAutoLine(drivetrain));
    // addCommands(new TimedAutoLine(drivetrain));
  }
}
