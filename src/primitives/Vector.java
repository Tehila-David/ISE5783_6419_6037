package primitives;

/**
 * The Vector class represents a mathematical vector in 3D space. It extends the Point class
 * and can be added to or subtracted from other vectors or points. It can also be scaled, dot-multiplied,
 * cross-multiplied, and normalized. The class provides methods to calculate the vector's length
 * and squared length.
 */
public class Vector extends Point {

    /**
     * Constructs a new vector with the specified x, y, and z components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalArgumentException if the vector's components are zero
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
    }

    /**
     * Constructs a new vector from the specified Double3 object.
     *
     * @param xyz the Double3 object to construct the vector from
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
    }

    /**
     * Returns a new vector that is the result of adding this vector to the specified vector.
     *
     * @param vec the vector to add to this vector
     * @return a new vector that is the result of adding this vector to the specified vector
     */
    public Vector add(Vector vec) {
        return new Vector(vec.xyz.add(this.xyz));
    }

    /**
     * Returns a new vector that is the result of scaling this vector by the specified factor.
     *
     * @param num the factor to scale this vector by
     * @return a new vector that is the result of scaling this vector by the specified factor
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * Returns the dot product of this vector and the specified vector.
     *
     * @param vector the vector to dot-multiply with this vector
     * @return the dot product of this vector and the specified vector
     */
    public double dotProduct(Vector vector) {
        return vector.xyz.d1 * xyz.d1 + vector.xyz.d2 * xyz.d2 + vector.xyz.d3 * xyz.d3;
    }

    /**
     * Returns a new vector that is the cross product of this vector and the specified vector.
     *
     * @param vec the vector to cross-multiply with this vector
     * @return a new vector that is the cross product of this vector and the specified vector
     */
    public Vector crossProduct(Vector vec) {
        double x = xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2;
        double y = xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3;
        double z = xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Returns the squared length of this vector.
     *
     * @return the squared length of this vector
     */
    public double lengthSquared() {
        return dotProduct(this);
        //TODO: check to replace and calc here
    }

    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Returns a new vector that is the normalized version of this vector.
     * The normalized vector has the same direction as this vector but with a length of 1.
     *
     * @return a new vector that is the normalized version of this vector
     */
    public Vector normalize() {
        double length = this.length();
        return new Vector(this.xyz.d1 / length, this.xyz.d2 / length, this.xyz.d3 / length);
    }

    @Override
    public String toString() {
        return super.toString();

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

