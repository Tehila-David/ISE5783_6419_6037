package primitives;

import java.util.List;

/**
 * Represents a ray in 3D space.
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     * Constructs a new Ray object with the specified direction vector and starting point.
     *
     * @param point  The starting point of the ray.
     * @param vector The direction vector of the ray.
     */
    public Ray(Point point, Vector vector) {
        this.p0 = point;
        this.dir = vector.normalize();
    }

    /**
     * Returns the starting point of this ray.
     *
     * @return The starting point of this ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the normalized direction vector of this ray.
     *
     * @return The normalized direction vector of this ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * returns the point on the ray that is in a given distance from the head of the ray
     *
     * @param t the distance from the head of the ray to point
     * @return the point that is in the given distance from the head of the ray = p0 + t*v.
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray ray)
            return this.p0.equals(ray.p0) && this.dir.equals(ray.dir);
        return false;
    }


    @Override
    public String toString() {
        return "Ray" +
                "p0=" + p0 +
                ", dir=" + dir;
    }


    /**
     * find the point that is closest to the head of the ray.
     *
     * @param points list of points.
     * @return the closest point to the head of the ray out of the points list.
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null) // no close point is available
            return null;

        Point closePoint = points.get(0);   // save the first point
        double distance = closePoint.distanceSquared(this.p0); // the distance between the first point and the start of the ray

        for (Point point : points) {
            double distanceOfPoint = point.distanceSquared(this.p0);
            if (distance > distanceOfPoint)   // if there is a closer point then 'point', replace the values
            {
                closePoint = point;
                distance = distanceOfPoint;
            }
        }
        return closePoint;
    }
}
