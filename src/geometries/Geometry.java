package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric shapes that can be intersected and have a surface normal at each point.
 */
public interface Geometry {

    /**
     * Returns the surface normal to the geometry at a given point.
     *
     * @param point the point on the geometry
     * @return the surface normal to the geometry at the given point
     */
    public Vector getNormal(Point point);
}
