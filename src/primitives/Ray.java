package primitives;

public class Ray {
    final Point p0;
    final Vector dir;

    public Ray(Vector vector,Point point)
    {
        this.p0=point;
        this.dir=vector.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray ray)
            return this.p0.equals(ray.p0) && this.dir.equals(ray.dir) ;
        return false;
    }

    @Override
    public String toString() {
        return "Ray" +
                "p0=" + p0 +
                ", dir=" + dir;
    }
}
