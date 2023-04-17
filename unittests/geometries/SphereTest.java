package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTest {

    /**
     * Test method for GetNormal
     */
    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(2,new Point(1,2,3));
        Vector normal = new Vector(0.0,0.0,-1.0);
        //Test if the method returns the normal
        assertEquals(normal, sphere.getNormal(new Point(1,2,2)), "ERROR: GetNormal() wrong value");
        //Test if the normal vector is equal to 1
        assertEquals(1, sphere.getNormal(new Point(1,2,2)).length(), "ERROR: length() wrong value");

    }
}