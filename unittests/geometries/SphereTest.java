package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere=new Sphere(2,new Point(1,2,3));
        Vector normal=new Vector(0.0,0.0,-1.0);
        assertEquals(normal, sphere.getNormal(new Point(1,2,2)), "ERROR: GetNormal() wrong value");
        assertEquals(1, sphere.getNormal(new Point(1,2,2)).length(), "ERROR: GetNormal() wrong value");

    }
}