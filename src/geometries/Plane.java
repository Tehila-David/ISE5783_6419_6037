package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import static primitives.Util.*;


/**
 * Represents a plane in 3D space by a point and a normal vector.
 */
public class Plane implements Geometry {

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
    public List<Point> findIntersections(Ray ray)
    {
        //t=n*(q0-Po)/n*v
        Vector v= ray.getDir();
        Point p0=ray.getP0();

        //Ray on the plane
        if(q0.equals(p0)){
            return null;
        }

        //n*(q0-p0)
       double nqp=normal.dotProduct(q0.subtract(p0));
              //Ray on the plane
              if(isZero(nqp)){
                  return null;
               }
       //n*v
        double nv= normal.dotProduct(v);

        if(isZero(nv))
        {
            return null;
        }
        double t = alignZero(nqp / nv);



        //Ray after the plane
        if(t<0)
        {
            return null;
        }
        Point P=ray.getPoint(t);


        //Ray crosses the plane
        return List.of(P);

    }


}
