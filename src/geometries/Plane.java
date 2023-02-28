package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane {

    final Point q0;
    final Vector normal;

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
    public Plane(Point p1,Point p2,Point p3) {
        normal=null;
        q0=p1;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }
}
