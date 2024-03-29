package primitives;

public class Material {

    /**
     * Factor of Diffuse
     */
    public Double3 kD = Double3.ZERO;
    /**
     * Factor of Specular
     */
    public Double3 kS = Double3.ZERO;
    /**
     * Factor of Refraction
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Reduction of Reflection
     */
    public Double3 kR = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Update action for Factor of Diffuse by Builder template
     * @param kD Factor of Diffuse, Double3
     * @return Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     *Update action for Factor of Specular by Builder template
     * @param kS Factor of specular , Double3
     * @return Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Update action for Factor of refraction by Builder template
     *
     * @param kT Factor of transparency, Double3
     * @return Material
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Update action for Factor of reflection by Builder template
     *
     * @param kR Factor of reflection, Double3
     * @return Material
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Update action for Factor of Diffuse by Builder template
     * @param kD Factor of Diffuse, double
     * @return Material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Update action for Factor of Specular by Builder template
     * @param kS Factor of Specular, double
     * @return Material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Update action for Factor of refraction by Builder template
     *
     * @param kT Factor of transparency, double
     * @return Material
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Update action for Factor of reflection by Builder template
     *
     * @param kR Factor of reflection, double
     * @return Material
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }


}
