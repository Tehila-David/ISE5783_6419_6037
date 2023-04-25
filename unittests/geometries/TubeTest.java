package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
class TubeTest {

    /**
     * Test method for GetNormal
     */
    @Test
    void testGetNormal()
    {
        Ray ray = new Ray(new Vector(0, 0, 1),new Point(0, 0, 0));
        Tube tube = new Tube(2, ray);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(0,tube.getNormal(new Point(2, 2, 10)).dotProduct(new Vector(0, 0, 1)),
                "Bad normal to tube");

        // =============== Boundary Values Tests ==================
        // TC11: Normal is orthogonal to the head of the axis Ray
        assertEquals(0,tube.getNormal(new Point(2, 2, 0)).dotProduct(new Vector(0, 0, 1)),
                "Bad normal to tube when the normal is orthogonal to the head of the axisRay");

    }
    
    @Test
    void testfindIntersections() {

    }



}