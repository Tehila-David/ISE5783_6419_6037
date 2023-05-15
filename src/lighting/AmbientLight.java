package lighting;

import primitives.Color;
import primitives.Double3;


public class AmbientLight
{
    Color intensity; //fill light intensity

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK,Double3.ZERO);

    /*constructor*/
    public AmbientLight(Color intensity, Double3 Ka)
    {
        this.intensity = intensity.scale(Ka);
    }
    /*constructor*/
    public AmbientLight(Color intensity, double Ka)
    {
        this.intensity = intensity.scale(Ka);
    }

    /*A method that returns the value of the ambient lighting intensity
    */
    public Color getIntensity()
    {
        return intensity;
    }


}
