package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.LinkedList;
public class Geometries implements Intersectable
{
     private List<Intersectable> geometries;



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
        for (int i =0; i<geometries.length; i++)
            this.geometries.add(geometries[i]);
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