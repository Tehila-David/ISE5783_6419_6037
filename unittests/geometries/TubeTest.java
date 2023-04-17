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
        // get normal-bonus
        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Vector(1,2,3),new Point(1,1,1));
        Tube tube = new Tube(4.3,ray);
        Vector normal = new Vector(0.0,0.0,-1.0);
        assertEquals(normal, tube.getNormal(new Point(1,2,2)), "ERROR: GetNormal() wrong value");
        assertEquals(1, tube.getNormal(new Point(1,2,2)).length(), "ERROR: length() wrong value");

        // =============== Boundary Values Tests ==================


    }

}