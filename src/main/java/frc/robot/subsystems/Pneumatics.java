package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.*;

public final class Pneumatics implements Subsystem {
    private static Pneumatics mInstance;

    public static PneumaticHub hub = new PneumaticHub(PneumaticConstants.CAN_ID);
    private Compressor compressor = hub.makeCompressor();

    public Pneumatics() {
    }

    public static Pneumatics getInstance() {
        if (mInstance == null) {
            mInstance = new Pneumatics();
        }

        return mInstance;
    }

    /** Turn on the compressor */
    public void enableCompressor() {
        compressor.enableDigital();
    }

    /** Turn off the compressor */
    public void disableCompressor() {
        compressor.disable();
    }

    /** Build an instance of the Solenoid class using the PH's CAN ID */
    public Solenoid getSolenoid(int channel) {
        return hub.makeSolenoid(channel);
    }

    /** Build an instance of the DoubleSolenoid class using the PH's CAN ID */
    public DoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel) {
        return hub.makeDoubleSolenoid(forwardChannel, reverseChannel);
    }

    @Override
    public void periodic() {
    }
}