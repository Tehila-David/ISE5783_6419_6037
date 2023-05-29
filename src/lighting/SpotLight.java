package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private final Vector direction;
    private int specularN = 1;

    /**
     * Constructor of Spot Light by color intensity , position , direction
     *@param intensity the  color intensity
     @param position the location of Point Light
     @param direction the Vector direction of the Spot Light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Getting a spot lighting source with a narrower skin beam
     * @param specularN
     * @return spotLight
     */
    public SpotLight setNarrowBeam(int specularN) {
        this.specularN = specularN;
        return this;
    }

    /**
     * method that return the intensity of the color at a certain point
     * @param point the selected point
     * @return color
     */
    @Override
    public Color getIntensity(Point point) {
        if(specularN==1)
           return super.getIntensity(point).scale(Math.max(0, this.direction.dotProduct(getL(point))));
        return super.getIntensity(point).scale(Math.pow(Math.max(0, this.direction.dotProduct(getL(point))),specularN));
    }
}
