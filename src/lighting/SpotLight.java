package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private final Vector direction;

    /*constructor*/
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public SpotLight setNarrowBeam(int i) {
        return this;
    }

    /*getIntensity*/
    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));
    }
}
