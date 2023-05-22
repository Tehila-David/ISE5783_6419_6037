package lighting;
import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * An interface that displays the light source object
 */

public interface LightSource
{

    /**
     * returns color of light intensity on a specific point
     * @param p the selected point
     * @return the light intensity on 'p'
     */
    public Color getIntensity(Point p);
    /**
     * returns a vector from the light source to a point
     * @param p the destination point of the vector
     * @return a vector from the reference point of the light source to 'p'
     */
    public Vector getL(Point p);
}
