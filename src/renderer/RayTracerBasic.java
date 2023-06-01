

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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INITIAL_K = new Double3(1.0);

    /**
     * constructor of RayTracerBasic
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *
     * @param geopoint
     * @param n
     * @param lightSource
     * @return
     */
    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource){
        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.
                findGeoIntersections(lightRay,
                        lightSource.getDistance(geopoint.point));
        if(intersections == null) return Double3.ONE;

        Double3 transparent = Double3.ONE; // full transparency
        for (GeoPoint gp : intersections) {
            transparent = transparent.product(gp.geometry.getMaterial().kT);
            if(transparent.subtract(new Double3(MIN_CALC_COLOR_K)).lowerThan(0))
                return Double3.ZERO; // no transparency
        }
        return transparent;
    }

    /**
     *
     * @param ray the ray to trace the scene with
     * @return color of closest Intersection to the beginning of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = findClosestIntersection(ray); // intersect the ray with the geometries
        if (gp == null)     // no intersection was found
            return this.scene.background;   // the background color
        return calcColor(gp, ray);                                   // return the calculated color of this point
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
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // recursive call

    }

    /**
     * calculating the local (diffuse + specular) and the global (reflection + refraction) effects - recursive function
     * @param intersection  to calculate its color
     * @param ray that intersected the point
     * @param level the depth of the recursion for the global effects
     * @param k the volume of the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray,k)
                .add(intersection.geometry.getEmission());
        return 1 == level ? color :
                color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
     * @return the calculated color on the point in the geoPoint
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray , Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;                                      // the camera doesn't see the light

        Material mat = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);            // from the lightSource to the geometry
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv) ->
                // the camera and the light source are on the same side of the surface

                Double3 ktr = transparency(geoPoint, n, lightSource);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {  // the light is not shaded by other geometries
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(mat, l, n, lightIntensity),
                            calcSpecular(mat, l, n, v, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     *
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) // the color is effected by the reflection
            color = calcGlobalEffects(constructReflectedRay(n,gp.point, ray), level, material.kR, kkr);

        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) // the color is effected due to the transparency
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point, ray, n), level, material.kT, kkt));

        return color;
    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * the diffusion effect on the object according to the phong reflection model
     * @param mat
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(Material mat, Vector l, Vector n, Color lightIntensity) {
        // the phong model formula for the diffusive effect: 𝒌𝑫 ∙| 𝒍 ∙ 𝒏 |∙ 𝑰
        return lightIntensity.scale(mat.kD.scale(Math.abs(n.dotProduct(l))));
    }

    /**
     * the specular effect on the object according to the phong reflection model
     * @param mat
     * @param l
     * @param n
     * @param v
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(Material mat, Vector l, Vector n, Vector v, Color lightIntensity) {
        Vector r = reflectionVector(l, n);    // the specular ray
        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(mat.kS.scale( alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                        mat.nShininess))));
    }

    /**
     * construct the refraction ray according to the physics law of refraction
     * @param point reference point of the new ray
     * @param ray the ray to refract
     * @return the refraction ray
     */
    private Ray constructRefractedRay(Point point, Ray ray,Vector normal) {
        return new Ray(point, ray.getDir(),normal);
    }

    /**
     * construct the reflection ray according to the physics law of reflection
     * @param point reference point of the new ray
     * @param ray the ray to reflect
     * @param normal the normal to the geometry the point belongs to
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector normal ,Point point, Ray ray) {
        return new Ray(point, reflectionVector(ray.getDir(), normal),normal);
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
