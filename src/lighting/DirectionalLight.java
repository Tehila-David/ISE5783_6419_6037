package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
* A class representation of a light source that has direction and intensity and no attenuation coefficients
*/
public class DirectionalLight extends Light implements LightSource
{
    private Vector direction;
    /**
     * Constructor of Directiona lLight by color intensity and direction
     *@param intensity the  color intensity
     @param direction the Vector direction of the Directional Light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p)
    {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }
}
