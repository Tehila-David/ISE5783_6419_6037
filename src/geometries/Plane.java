package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;


/**
 * Represents a plane in 3D space by a point and a normal vector.
 */
public class Plane extends Geometry {

    final private Point q0;
    final private Vector normal;

    /**
     * Constructs a plane by a point on the plane and a normal vector to the plane.
     *
     * @param q0 the point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a plane by three points on the plane.
     *
     * @param p1 the first point on the plane
     * @param p2 the second point on the plane
     * @param p3 the third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
        q0 = p1;
    }

    /**
     * Returns the point on the plane.
     *
     * @return the point on the plane
     */
    public Point getQ0() {
        return q0;
    }

    @Override
    /**
     * Returns the normal vector to the plane.
     *
     * @return the normal vector to the plane
     */
    public Vector getNormal(Point point) {
        return normal;
    }
    public Vector getNormal()
    {
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if(this.q0.equals(ray.getP0())) //ray starts at the reference point of the plane
            return null;

        Vector vecFromRayToNormal = this.q0.subtract(ray.getP0());
        double numerator = this.normal.dotProduct(vecFromRayToNormal);
        if(isZero(numerator)) // ray starts on the plane
            return null;

        double denominator = this.normal.dotProduct(ray.getDir());
        if(isZero(denominator)) // ray is parallel to the plane
            return null;

        double t = numerator / denominator;
        if(t < 0 || alignZero(t - maxDistance) > 0) // ray starts after the plane
            return null;

        List<GeoPoint> intersections = new LinkedList<>();
        intersections.add(new GeoPoint(this, ray.getPoint(t)));
        return intersections;
    }
}
