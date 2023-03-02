package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    final private Point center;
    final private double radius;

    public Sphere(double radius, Point center, double radius1) {
        super(radius);
        this.center = center;
        this.radius = radius1;
    }

    public Vector getNormal(Point point)
    {
        return null;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return super.toString() +
                "center=" + center +
                ", radius=" + radius;
    }
}
