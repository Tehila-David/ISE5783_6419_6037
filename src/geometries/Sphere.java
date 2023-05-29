
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.ArrayList;
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new ArrayList<>();

        if(this.center.equals(ray.getP0())){
            intersections.add(new GeoPoint(this, ray.getPoint(radius)));
            return intersections;
        }

        Vector u = this.center.subtract(ray.getP0());       // vector from ray to the sphere's center

        double tm = u.dotProduct(ray.getDir());             // projection of u on ray
        double dSquared =u.lengthSquared() - tm * tm;  // Pythagoras - distance from sphere's center to the ray

        if (alignZero(dSquared - this.radius * this.radius) >= 0)         // ray crosses outside the sphere
            return null;

        double th = alignZero(Math.sqrt(this.radius * this.radius - dSquared));
        // t1, t2 are the units to extend dir vec inorder to get the intersections
        double t1 = tm + th;
        double t2 = tm - th;

        if (alignZero(t1) <= 0 && alignZero(t2) <= 0)       // intersects on the opposite direction of ray
            return null;

        if(alignZero(t1) > 0 && alignZero(t1 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t1)));
        if(alignZero(t2) > 0 && alignZero(t2 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t2)));

        return intersections;
    }
}
