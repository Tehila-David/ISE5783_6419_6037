package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
public class Geometries extends Intersectable
{
     private final List<Intersectable> geometries;

    public Geometries()
    {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersectable...geometries)
    {
        this.geometries = new LinkedList<>();
        this.add(geometries);
    }
    public void add(Intersectable...geometries)
    {
        Collections.addAll(this.geometries, geometries);
    }
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> result = null;

        for (Intersectable geo : geometries) {
            List<Point> points = geo.findIntersections(ray);
            if (points != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(points);
            }
        }
        return result;

    }

}