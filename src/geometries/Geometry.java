package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric shapes that can be intersected and have a surface normal at each point.
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }


    /**
     * setter for the emission color. builder pattern
     *
     * @param emission to set to the geometry.
     * @return the geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the surface normal to the geometry at a given point.
     *
     * @param point the point on the geometry
     * @return the surface normal to the geometry at the given point
     */
    public abstract Vector getNormal(Point point);

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
