package renderer;

import primitives.*;
import primitives.Color;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import lighting.LightSource;

import geometries.Intersectable.GeoPoint;


public class RayTracerBasic extends RayTracerBase {

    /*constructor*/
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray); // intersect the ray with the geometries
        if (intersectionPoints == null)     // no intersection was found
            return this.scene.background;   // the background color

        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);   // find the closest point - the point the ray 'sees' first
        return calcColor(point, ray);

    }

    /**
     * calculating the color of a specific point, taking into account the lightning,
     * transparency of the point itself and other affects of the surrounding are of the point in space
     * @param geoPoint calculate the color of this point
     * @return for now - the ambient light's intensity
     */

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * calculate the effects of the lighting on the intensity of the point
     *
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray      the ray of the camera pointing to the GeoPoint's geometry
     * @return the calculated color on the point in the geoPoint
     */

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = geoPoint.geometry.getEmission();
        Vector vector = ray.getDir();        // the vector from the camera to the geometry
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);     // the normal to the geometry
        double nv = alignZero(normal.dotProduct(vector));
        if (nv == 0)
            return color;

        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point); //  // vec from the lightSource to the geometry
            double nl = alignZero(normal.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(geoPoint.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, normal, l, nl, vector)));
            }
        }
        return color;
    }


    private Double3 calcDiffusive(Material material, double nl) {
        if(nl<0)
            return material.kD.scale(-nl);
        return material.kD.scale(nl);
    }


    private Double3 calcSpecular(Material material, Vector normal, Vector l, double nl, Vector vector) {
        Vector r = l.subtract(normal.scale(2 * nl));    // the specular ray
        return material.kS.scale(Math.pow(Math.max(0,vector.scale(-1).dotProduct(r)),material.nShininess));
    }


}
