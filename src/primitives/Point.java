package primitives;

import java.util.Objects;
import java.util.Vector;

public class Point {
    Double3 xyz;

    public Point(double x,double y,double z)
    {
        this.xyz=new Double3(x,y,z);
    }

     Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point point)
            return this.xyz.equals(point.xyz);
        return false;
    }

    @Override
    public String toString() {
        return "Point: " + xyz;
    }

    public Point add(Vector vector)
    {
        return new Point(xyz.add(vector.xyz));
    }
    public Point subtract(Vector vector)
    {
        return new Point(xyz.subtract(vector.xyz));
    }

    public double distanceSquared(Point point)
    {
        return (point.xyz.d1 - xyz.d1)*(point.xyz.d1 - xyz.d1)+
                (point.xyz.d2 - xyz.d2)*(point.xyz.d2 - xyz.d2)+
                (point.xyz.d3 - xyz.d3)*(point.xyz.d3 - xyz.d3);
    }
    public double distance(Point point)
    {
        return Math.sqrt(distanceSquared(point));
    }




}




