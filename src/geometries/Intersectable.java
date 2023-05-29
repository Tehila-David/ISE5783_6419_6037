package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
/**
 * Gives interface for an object that is instersectable.
 */
public abstract class Intersectable
{
    /**
     * an internal class to represent a geometry and an intersection point on the geometry.
     * need this in order to find the color on the specific point on the geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /*constructor*/
        public GeoPoint(Geometry geometry, Point point)
        {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.point.equals(geoPoint.point) && this.geometry.equals(geoPoint.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometries=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }



    /**
     * @param ray intersection in geometries
     * @return list of intersectables the the ray intersecte in geometries
     */
    public final List<Point> findIntersections(Ray ray)
    {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);}
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    /**
     * find intersections between a ray and a geometry
     * @param ray the ray to intersect with
     * @return a list with GeoPoints that represent the intersections between the ray and the geometry
     */
    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);


}
