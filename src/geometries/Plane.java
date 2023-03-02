package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in 3D space by a point and a normal vector.
 */
public class Plane{

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
        normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
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

    /**
     * Returns the normal vector to the plane.
     *
     * @return the normal vector to the plane
     */
    public Vector getNormal() {
        return normal;
    }


}
