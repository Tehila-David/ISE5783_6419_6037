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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> Intersections = new LinkedList<>();

        for (Intersectable intersectable: this.geometries) {
            List<GeoPoint> currentIntersection = intersectable.findGeoIntersectionsHelper(ray, maxDistance);
            if(currentIntersection != null) // intersection was found
                Intersections.addAll(currentIntersection);
        }

        if(Intersections.size() == 0)
            return null;
        return Intersections;
    }







}