package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.Ray;

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
        assertEquals(normal, plane.getNormal(p1), "ERROR: GetNormal() wrong value");
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
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        //**** The Ray must be neither orthogonal nor parallel to the plane
        // TC01: Ray intersects the plane (1 point)
        result=plane.findIntersections(new Ray(new Point(0,1,1),new Vector(0,0,-1)));
        assertEquals(1,result.size(),"Ray intersects the plane - Wrong number of points");
        assertEquals(new Point(0,1,0),result.get(0),"Ray intersects the plane- wrong point");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,1,1),new Vector(0,0,1))),"Ray doesn't intersect the plane-not return null");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC11: Ray is included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,-1,0))),"Ray is included in the plane- not return null");

        //TC12: Ray isn't included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,2), new Vector(1,-1,0))),"Ray isn't included in the plane - not return null");

        //**** Group: Ray is orthogonal to the plane
        //TC13: Ray starts before the plane (1 point)
        result=plane.findIntersections(new Ray(new Point(-1,-1,-1),new Vector(1,1,1)));
        double n = 0.3333333333333335;    //(1/3)

        assertEquals(1,result.size(),"Ray starts before the plane-Wrong number of points");
        assertEquals(new Point(n,n,n),result.get(0)," Ray starts before the plane-wrong point");

        //TC14: Ray starts inside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,1,1))),"Ray starts inside the plane. Ray is orthogonal to the plane");

        //TC15: Ray starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,2,2),new Vector(1,1,1))),"Ray starts after the plane - not return null");

        //**** Group: Special case
        //TC16: Ray begins at the plane (p0 is in the plane, but not the ray)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0),new Vector(0,0,-1))),"Ray begins at the plane - not return null");

        //TC17: Ray begins in the plane's reference point
        assertNull(plane.findIntersections(new Ray(plane.getQ0(),new Vector(1,0,0))),"Ray begins in the plane's reference point- not return null");
    }


}