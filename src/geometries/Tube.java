package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Tube class represents a tube in 3D space, defined by a center point, a radius, and an axis ray.
 * It extends the RadialGeometry class.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis ray of the tube.
     */
    final protected Ray axisRay;

    /**
     * Constructs a Tube object with the given radius and axis ray.
     *
     * @param radius   the radius of the tube
     * @param axisRay  the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis ray of the tube.
     *
     * @return the axis ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns the normal vector to the tube at the given point.
     * Since a tube is infinite in length, there is no unique normal vector to any point on the tube.
     * Therefore, this method returns null.
     *
     * @param point  the point on the tube for which to calculate the normal vector
     * @return null, since a tube has no unique normal vector at any point
     */
    public Vector getNormal(Point point) {
        return null;
    }//bonus

    @Override
    public String toString() {
        return super.toString() +
                " axisRay=" + axisRay;
    }
}
