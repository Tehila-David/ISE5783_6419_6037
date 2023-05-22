
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
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
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        if (p0.equals(center))
        {
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        }

        Vector u = center.subtract(p0);
        double tm =v.dotProduct(u);
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        }

        if (t1 > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }

        if (t2 > 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }
        return null;
    }
}
