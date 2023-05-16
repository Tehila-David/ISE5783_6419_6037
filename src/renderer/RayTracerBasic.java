package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Point;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;


public class RayTracerBasic extends RayTracerBase
{

    /*constructor*/
    public RayTracerBasic(Scene scene)
    {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray)
    {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray); // intersect the ray with the geometries
        if (intersectionPoints == null)     // no intersection was found
            return this.scene.background;   // the background color

        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);   // find the closest point - the point the ray 'sees' first
        return calcColor(point);


    }

    /**
     * calculating the color of a specific point, taking into account the lightning,
     * transparency of the point itself and other affects of the surrounding are of the point in space
     * @param geoPoint calculate the color of this point
     * @return for now - the ambient light's intensity
     */

    private Color calcColor(GeoPoint geoPoint)
    {
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }

}
