package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTest {

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
        Plane plane = new Plane(p1,p2,p3);
        Vector normal=new Vector(1.0,0.0,-0.0);
        //TC01:Test if the method returns the normal
        assertEquals(normal, plane.getNormal(), "ERROR: GetNormal() wrong value");
        //TC02:Test if the normal vector is equal to 1
        assertEquals(1,plane.getNormal().length(),"ERROR: length() wrong value");
    }

    @Test
    void testConstructor()
    {
        // =============== Boundary Values Tests ==================
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(1,1,1);
        Point p3 = new Point(1,1,1);
        //TC11:The test checks if the first and second points converge
        assertThrows(IllegalArgumentException.class,()-> new Plane(p1,p2,p3), "ERROR: The points converge ");

        Point p4 =new Point(2,4,6);
        Point p5 = new Point(4,8,12);
        //TC12:The test checks if the points are on the same line
        assertThrows(IllegalArgumentException.class,()-> new Plane(p1,p4,p5),"ERROR: points are on the same line ");

    }

    @Test
    void testfindIntersections()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: 

    }
}