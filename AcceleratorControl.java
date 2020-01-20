package org.firstinspires.ftc.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

public class AcceleratorControl {
    private double storedAcceleratorValue;
    private ElapsedTime elapsedTime;
    private double currentPower = 0;

    public AcceleratorControl(double acceleration) {
        setAcceleration(acceleration);
    }

    public void setAcceleration(double value) {
        storedAcceleratorValue = value;
    }

    public double getAccelerationValue() {
        return storedAcceleratorValue;
    }

    public double update(double newPower) {
        if (elapsedTime == null)
        {
            elapsedTime = new ElapsedTime();
            return newPower;
        }

        double delta = Math.min(elapsedTime.seconds() * storedAcceleratorValue, Math.abs(newPower - currentPower));
        elapsedTime.reset();

        currentPower += Math.signum(newPower - currentPower) * delta;
        return currentPower;
    }
}
