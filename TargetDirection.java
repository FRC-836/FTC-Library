package org.firstinspires.ftc.utilities;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class TargetDirection {
    // Private Variables
    private static double absoluteHeadingAtFieldZero;
    private static BNO055IMU imu;

    private double fieldHeadingAtTargetZero;

    // Constructor, to be used by static functions.
    private TargetDirection(double fieldHeadingAtTargetZero) {
        this.fieldHeadingAtTargetZero = fieldHeadingAtTargetZero;
    }

    public static void setupImu(HardwareMap hardwareMap, boolean forceOverride) {
        if (imu == null || forceOverride) {
            imu = hardwareMap.get(BNO055IMU.class, "imu");
            BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
            imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            imuParameters.loggingEnabled = true;
            imuParameters.loggingTag = "IMU";
            imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imuParameters.temperatureUnit = BNO055IMU.TempUnit.FARENHEIT;

            imu.initialize(imuParameters);
        }
    }

    public static void setupImu(HardwareMap hardwareMap) {
        setupImu(hardwareMap, false);
    }

    public static void setCurrentHeading(double robotsCurrentHeading){
        TargetDirection.absoluteHeadingAtFieldZero = getAbsoluteHeading() - robotsCurrentHeading;
    }

    // Public functions to change directions
    public void moveTargetToRight(double degrees) { fieldHeadingAtTargetZero += degrees; }
    public void moveTargetToLeft(double degrees) { fieldHeadingAtTargetZero -= degrees; }
    public void setToFieldDirection(double degrees) { this.fieldHeadingAtTargetZero = degrees; }

    // Calculation functions
    private static double getAbsoluteHeading() {
        Orientation angles;
        synchronized (imu) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        }
        return errorCorrecter(-angles.firstAngle);
    }
    private static double calculateFieldHeading() {
        return errorCorrecter(getAbsoluteHeading() - absoluteHeadingAtFieldZero);
    }
    public static double getHeading() { return calculateFieldHeading(); }
    public double calculateDistanceFromTarget() {
        return errorCorrecter(calculateFieldHeading() - fieldHeadingAtTargetZero);
    }

    // Functions to create TargetDirection objects
    public static TargetDirection makeTargetAtFieldPosition(double fieldPositionInDegrees) { return new TargetDirection(fieldPositionInDegrees); }
    public static TargetDirection makeTargetToRobotsRight(double degreesToRight) { return new TargetDirection(calculateFieldHeading() + degreesToRight); }
    public static TargetDirection makeTargetToRobotsLeft(double degreesToLeft) { return new TargetDirection(calculateFieldHeading() - degreesToLeft); }

    // Error correction to make everything on the range -180 to +180
    private static double errorCorrecter(double heading){
        if (heading > 180f)
            heading = ((heading + 180f) % 360f) - 180f;
        else if (heading < -180f)
            heading = 180f - ((180f - heading) % 360f);
        return heading;
    }

    public double getFocusHeading() {
        return fieldHeadingAtTargetZero;
    }
}
