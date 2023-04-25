
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Sphere class represents a sphere in 3D space, defined by a center point and a radius.
 * It extends the RadialGeometry class.
 */
public class Sphere extends RadialGeometry {

    final private Point center;

    /**
     * Constructs a Sphere object with the given radius and center point.
     *
     * @param radius  the radius of the sphere
     * @param center  the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the sphere at the given point.
     * The normal vector to a sphere at any point on its surface is a vector from the center of the sphere to that point.
     *
     * @param point  the point on the sphere for which to calculate the normal vector
     * @return a vector from the center of the sphere to the given point
     */
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return super.toString() +
                " center=" + center;
    }

    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }
}
