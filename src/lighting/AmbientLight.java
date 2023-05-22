package lighting;

import primitives.Color;
import primitives.Double3;

/**
* A class that represents the ambient lighting in the scene
*/
public class AmbientLight extends Light
{
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK,Double3.ZERO);
/**
* Constructor of Ambient lighting by color intensity and landing factor
*@param Ia the  color intensity
 @param Ka the landing factor
 */
    public AmbientLight(Color Ia,Double3 Ka)
    {
        super(Ia.scale(Ka));
    }
    public AmbientLight(Color Ia, double Ka)
    {
        super(Ia.scale(Ka));
    }
}
