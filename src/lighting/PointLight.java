package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * A class representation of a light source that has location, intensity and attenuation coefficients
 */
public class PointLight extends Light implements LightSource
{
    private Point position;
    double kC=1;
    double kL=0;
    double kQ=0;

    /**
     * Constructor of Point Light by color intensity and position
     *@param intensity the  color intensity
     @param position the location of Point Light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point)
    {
        double d = point.distance(this.position);// d - the distance from the point light
        return this.getIntensity().reduce (kC + kL * d + kQ * d* d);
    }

    @Override
    public Vector getL(Point point)
    {
        return point.subtract(this.position).normalize();
    }

    /**
     * set the kC attenuation factor
     * @param kC the attenuation factor
     * @return the point light. by builder pattern
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * set the kL attenuation factor
     * @param kL the attenuation factor
     * @return the point light. by builder pattern
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * set the kQ attenuation factor
     * @param kQ the attenuation factor
     * @return the point light. by builder pattern
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
