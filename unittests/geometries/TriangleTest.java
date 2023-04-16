package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.concurrent.TransferQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for GetNormal
     */
    @Test
    void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(1,3,6);
        Point p3 = new Point(1,1,1);
        Triangle triangle = new Triangle(p1,p2,p3);
        //Vector normal=new Vector(0.0,0.0,-1.0);
        Vector normal=new Vector(1.0,0.0,-0.0);
        assertEquals(normal, triangle.getNormal(new Point(1,2,2)), "ERROR: GetNormal() wrong value");
        assertEquals(1, triangle.getNormal(new Point(1,2,2)).length(), "ERROR: length() wrong value");

    }
}