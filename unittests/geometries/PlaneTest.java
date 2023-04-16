package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaneTest {

    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(1,3,6);
        Point p3 = new Point(1,1,1);
        Plane plane = new Plane(p1,p2,p3);
        Vector normal=new Vector(1.0,0.0,-0.0);
        assertEquals(normal, plane.getNormal(), "ERROR: GetNormal() wrong value");
        assertEquals(1,plane.getNormal().length(),"ERROR: GetNormal() wrong value");
    }

    @Test
    void testPlane()
    {
    }
}