/**
 * The Cylinder class represents a cylinder in 3D space, defined by a center point, a radius, an axis ray, and a height.
 * It extends the Tube class.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    final private double height;

    /**
     * Constructs a Cylinder object with the given radius, axis ray, and height.
     *
     * @param radius   the radius of the cylinder
     * @param axisRay  the axis ray of the cylinder
     * @param height   the height of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }


    @Override
    public String toString() {
        return super.toString() +
                " height=" + height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the normal vector to the cylinder at the given point.
     * Since a cylinder is infinite in length, there is no unique normal vector to any point on the cylinder.
     * Therefore, this method returns null.
     *
     * @param point  the point on the cylinder for which to calculate the normal vector
     * @return null, since a cylinder has no unique normal vector at any point
     */
    public Vector getNormal(Point point) {
        return null;
    }

}
