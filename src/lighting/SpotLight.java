package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private final Vector direction;
    private int specularN = 1;

    /*constructor*/
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
   
    public SpotLight setNarrowBeam(int specularN) {
        this.specularN = specularN;
        return this;
    }

    /*getIntensity*/
    @Override
    public Color getIntensity(Point point) {
        if(specularN==1)
           return super.getIntensity(point).scale(Math.max(0, this.direction.dotProduct(getL(point))));
        return super.getIntensity(point).scale(Math.pow(Math.max(0, this.direction.dotProduct(getL(point))),specularN));
    }
}
