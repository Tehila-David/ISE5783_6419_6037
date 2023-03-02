package primitives;

import java.util.Objects;
import java.util.Vector;

/**
 * Represents a 3D point in space.
 */
public class Point {
    /**
     * The coordinates of this point.
     */
    final protected Double3 xyz;

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object with the specified Double3 object.
     *
     * @param xyz The Double3 object that contains the coordinates of the point.
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point point)
            return this.xyz.equals(point.xyz);
        return false;
    }

    @Override
    public String toString() {
        return "Point: " + xyz;
    }

    /**
     * Returns a new Point object that is the result of adding the specified vector to this point.
     *
     * @param vector The vector to add to this point.
     * @return A new Point object that is the result of adding the specified vector to this point.
     */
    public Point add(primitives.Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Returns a new Vector object that is the result of subtracting the specified point from this point.
     *
     * @param point The point to subtract from this point.
     * @return A new Vector object that is the result of subtracting the specified point from this point.
     */
    public primitives.Vector subtract(Point point) {
        return new primitives.Vector(xyz.subtract(point.xyz));
    }

    /**
     * Returns the square of the distance between this point and the specified point.
     *
     * @param point The point to calculate the distance to.
     * @return The square of the distance between this point and the specified point.
     */
    public double distanceSquared(Point point) {
        return (point.xyz.d1 - xyz.d1)*(point.xyz.d1 - xyz.d1)+
                (point.xyz.d2 - xyz.d2)*(point.xyz.d2 - xyz.d2)+
                (point.xyz.d3 - xyz.d3)*(point.xyz.d3 - xyz.d3);
    }

    /**
     * Returns the distance between this point and the specified point.
     *
     * @param point The point to calculate the distance to.
     * @return The distance between this point and the specified point.
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
}





