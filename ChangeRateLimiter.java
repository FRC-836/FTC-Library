package org.firstinspires.ftc.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ChangeRateLimiter {
    private double maxChangeRate;
    private double currentValue = 0.0;
    private double currentGoal = 0.0;
    private ElapsedTime timer;
    private double lastTime = 0.0;

    public ChangeRateLimiter(double maxChangeRate) {
        setMaxChangeRate(maxChangeRate);
    }

    public void init(double initialValue) {
        currentValue = initialValue;
        currentGoal = initialValue;
    }

    public void setMaxChangeRate(double value) {
        maxChangeRate = value;
    }
    public double getMaxChangeRate() {
        return maxChangeRate;
    }

    public double update() {
        if (timer == null)
        {
            timer = new ElapsedTime();
            return currentValue;
        }

        double time = timer.seconds();
        double dt = time - lastTime;
        lastTime = time;

        double delta = Math.min(dt * maxChangeRate, Math.abs(currentGoal - currentValue));

        currentValue += Math.signum(currentGoal - currentValue) * delta;
        return currentValue;
    }

    public double update(double currentGoal) {
        this.currentGoal = currentGoal;
        return update();
    }
}
