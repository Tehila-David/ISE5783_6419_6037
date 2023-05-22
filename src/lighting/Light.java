package lighting;
import primitives.Color;
/**
 * representation of a light
 */
abstract class Light
{
    private Color intensity;

    /**
    *constructor of light
    */
    protected Light(Color intensity)
    {
        this.intensity = intensity;
    }
    /**
    * getter method of intensity
    * @return the intensity of color
    */
    public Color getIntensity() {
        return intensity;
    }

}
