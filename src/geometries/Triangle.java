package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;
import java.util.LinkedList;

/**
 * The Triangle class represents a triangle in 3D space. It extends the Polygon class and is defined by three vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle object with the given vertices.
     *
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


    //  public Vector getNormal(Point point) {
    //    return null;
    //  }

    @Override
    public String toString() {
        return super.toString() +
                "vertices=" + vertices +
                ", plane=" + plane;
    }

    @Override
    public  List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = this.plane.findIntersections(ray);
        if (intersections == null) //check if the ray not intersect triangle
            return null;

        // vectors from the start of the ray to each vertex of the triangle
        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector v3 = this.vertices.get(2).subtract(ray.getP0());

        try {
            // cross product of following vectors -> the normal vector for each couple of vectors
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();

            // dot product between the normals and the ray
            double d1 = alignZero(ray.getDir().dotProduct(n1));
            double d2 = alignZero(ray.getDir().dotProduct(n2));
            double d3 = alignZero(ray.getDir().dotProduct(n3));

            // if the d's have different sign, there is no intersection
            if (!(d1 > 0 && d2 > 0 && d3 > 0) && !(d1 < 0 && d2 < 0 && d3 < 0))
                return null;

            List<GeoPoint> geoPointsTriangle = new LinkedList<>();
            geoPointsTriangle.add(new GeoPoint(this, intersections.get(0)));
            return geoPointsTriangle;

        } catch (Exception x) {
            // one of the cross products constructed the zero vector -> intersect the vertex or the edge of the triangle
            return null;
        }
    }
}




