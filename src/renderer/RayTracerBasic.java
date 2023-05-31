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
    /**
     * A fixed number for the size of the ray head shift for shading rays
     */
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * constructor of RayTracerBasic
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *
     * @param ray the ray to trace the scene with
     * @return color
     */
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
     *
     * @param geoPoint calculate the color of this point
     * @return for now - the ambient light's intensity
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));

    }

    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
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
            Vector l = lightSource.getL(geoPoint.point); // vec from the lightSource to the geometry
            double nl = alignZero(normal.dotProduct(l));
            if (nl * nv > 0  // sign(nl) == sing(nv) ->
                    // the camera and the light source are on the same side of the surface
                    && unshaded(geoPoint, normal, lightSource)) {
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                        lightIntensity.scale(calcSpecular(material, normal, l, nl, vector)));
                }
            }
        return color;
    }

    /**
     * the diffusion effect on the object according to the phong reflection model
     *
     * @param material diffusion factor
     * @param nl vector n dot product vector l
     * @return calculated intensity with the diffusive effect
     */
    private Double3 calcDiffusive(Material material, double nl) {
        if (nl < 0)
            return material.kD.scale(-nl);
        return material.kD.scale(nl);
    }

    /**
     * the specular effect on the object according to the phong reflection model
     * @param material specular factor
     * @param normal   normal vec to the point on the geometry
     * @param l vec from the light source to a point on the geometry
     * @param vector vec from the camera to the geometry = the camera's eye
     * @param nl vector n dot product vector l
     * @return calculated intensity with the specular effect
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector l, double nl, Vector vector) {
        Vector r = l.subtract(normal.scale(2 * nl));    // the specular ray
        return material.kS.scale(Math.pow(Math.max(0, vector.scale(-1).dotProduct(r)), material.nShininess));
    }

    /**
     * A method of checking non-shading between a point and the light source
     * @param geopoint
     * @param n
     * @param lightSource
     * @return true if unshaded
     */
    private boolean unshaded(GeoPoint geopoint, Vector n, LightSource lightSource) {

        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point.add(n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA)), lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,lightSource.getDistance(geopoint.point));
        return intersections == null;
    }

    /**
     * construct the refraction ray according to the physics law of refraction
     * @param point reference point of the new ray
     * @param ray the ray to refract
     * @return the refraction ray
     */
    private Ray constructRefractedRay(Point point, Ray ray) {
        return new Ray(point, ray.getDir());
    }

    /**
     * construct the reflection ray according to the physics law of reflection
     * @param point reference point of the new ray
     * @param ray the ray to reflect
     * @param normal the normal to the geometry the point belongs to
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector normal ,Point point, Ray ray) {
        return new Ray(point, reflectionVector(ray.getDir(), normal));
    }

    /**
     * calculate the reflected vector using linear algebra and projection on a normal vector
     * @param l the vector to reflect
     * @param n the normal vector to reflect by
     * @return the reflected vector
     */
    private Vector reflectionVector(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }

    /**
     * intersects the ray with the scene and finds the closest point the ray intersects
     * @param ray to intersect the scene with
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersectionPoints);
    }

}
