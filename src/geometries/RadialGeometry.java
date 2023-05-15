package geometries;

/**
 * Abstract class for radial geometric shapes.
 * A radial geometry is a shape defined by a radius value.
 * It implements the Geometry interface.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the radial geometry.
     */
    final protected double radius;

    /**
     * Constructs a radial geometry with a given radius value.
     *
     * @param radius the radius value of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
