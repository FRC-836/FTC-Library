package org.firstinspires.ftc.utilities;

public class Angle {
    private static final double TAU = 2 * Math.PI;
    private double radians;

    public enum Unit {
        DEGREES,
        RADIANS,
        ROTATIONS
    }

    public Angle(double value, Unit type) {
        switch (type) {
            case DEGREES:
                radians = Math.toRadians(value);
                break;
            case RADIANS:
                radians = value;
                break;
            case ROTATIONS:
                radians = value * TAU;
                break;
            default:
                throw new IllegalArgumentException("Angle: Unknown Unit type provided");
        }
    }

    public static Angle radians(double value) {
        return new Angle(value, Unit.RADIANS);
    }
    public static Angle degrees(double value) {
        return new Angle(value, Unit.DEGREES);
    }
    public static Angle rotations(double value) {
        return new Angle(value, Unit.ROTATIONS);
    }

    public double getValue(Unit unitType) {
        switch (unitType) {
            case RADIANS:
                return radians;
            case DEGREES:
                return Math.toDegrees(radians);
            case ROTATIONS:
                return radians / TAU;
            default:
                throw new IllegalArgumentException("Angle: Unknown Unit type provided");
        }
    }

    static public Angle add(Angle angle1, Angle angle2) {
        return radians(angle1.radians + angle2.radians);
    }
    public void add(Angle angle) {
        radians += angle.radians;
    }

    static public Angle subtract(Angle angle1, Angle angle2) {
        return radians(angle1.radians - angle2.radians);
    }
    public void subtract(Angle angle) {
        radians -= angle.radians;
    }

    public Angle copy() {
        return Angle.radians(radians);
    }

    static public Angle restrain(Angle angle) {
        Angle newAngle = angle.copy();
        newAngle.restrain();
        return newAngle;
    }
    public void restrain() {
        radians = ((radians % TAU) + TAU) % TAU;
        if (radians > Math.PI)
            radians -= TAU;
    }
}
