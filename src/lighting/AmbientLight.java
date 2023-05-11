package lighting;

import primitives.Color;
import primitives.Double3;

import java.security.PublicKey;

public class AmbientLight
{
    Color intensity; //fill light intensity

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK,Double3.ZERO);

    /*constructor*/
    public AmbientLight(Color Ia, Double3 Ka)
    {
        intensity = Ia.scale(Ka);
    }
    /*constructor*/
    public AmbientLight(Color Ia, double Ka)
    {
        intensity = Ia.scale(Ka);
    }

    /*A method that returns the value of the ambient lighting intensity
    */
    public Color getIntensity()
    {
        return intensity;
    }


}
