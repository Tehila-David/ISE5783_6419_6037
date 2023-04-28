package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
/**
 * Gives interface for an object that is instersectable.
 */
public interface Intersectable
{
    /**
     * @param ray intersection in geometries
     * @return list of intersectables the the ray intersecte in geometries
     */
    List<Point> findIntersections(Ray ray);
    
}
