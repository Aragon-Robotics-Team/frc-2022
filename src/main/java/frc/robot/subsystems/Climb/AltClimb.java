package main.java.frc.robot.subsystems.Climb;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class AltClimb extends SubsystemBase {
  public static final class Config {
    public static final int kMotorPort = 0;
  }

  /** Creates a new Climb. */

  //Create Pneumatic Pistons
    private final DoubleSolenoid m_solenoidLeft = new DoubleSolenoid(Config.kPCMId, Config.kForwardChannel, Config.kReverseChannel);
    private final DoubleSolenoid m_solenoidRight = new DoubleSolenoid(Config.kPCMId, Config.kForwardChannel, Config.kReverseChannel);

  public Climb() {
    m_encoder.setPositionConversionFactor(1.0);
  }


  public double getEncoderValue() {
    return m_encoder.getPosition();
  }

  public void resetEncoder() {
    m_encoder.setPosition(0.0);
  }

  public void set(double speed) {
    m_motor.set(speed);
  }

  private void solenoidSet(Value v) {
    m_solenoidLeft.set(v);
  }

  public void armUp() {
    solenoidSet(Value.kForward);
  }

  public void armDown() {
    solenoidSet(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climb/encoderValue", getEncoderValue());

  }
  }

