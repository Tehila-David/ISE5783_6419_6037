package primitives;
public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
    }

    Vector(Double3 vec) {
        this(vec.d1,vec.d2, vec.d3);
    }

    public Vector add(Vector vec)
    {
        return new Vector(vec.xyz.add(this.xyz));
    }
    public Vector scale(double num)
    {
        return new Vector(xyz.scale(num));
    }

    public double dotProduct(Vector vec)
    {
        return vec.xyz.d1 * xyz.d1 + vec.xyz.d2 * xyz.d2 + vec.xyz.d3 * xyz.d3;
    }

    public Vector crossProduct(Vector vec)
    {
        double x=xyz.d2*vec.xyz.d3 -xyz.d3*vec.xyz.d2;
        double y=xyz.d3*vec.xyz.d1 -xyz.d1*vec.xyz.d3;
        double z=xyz.d1*vec.xyz.d2 -xyz.d2*vec.xyz.d1;
        return new Vector(x,y,z);
    }

    public double lengthSquared()
    {
        return dotProduct(this);
    }

    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize()
    {
        return new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length());
    }

    @Override
    public String toString() {
        return super.toString();

    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

