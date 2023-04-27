package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

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
        Vector normal=new Vector(1.0,0.0,-0.0);
        //TC01: Test if the method returns the normal
        assertEquals(normal, triangle.getNormal(new Point(1,2,2)), "ERROR: GetNormal() wrong value");
        //TC02: Test if the normal vector is equal to 1
        assertEquals(1, triangle.getNormal(new Point(1,2,2)).length(), "ERROR: length() wrong value");

    }

    @Test
    void testfindIntersections()
    {
        Triangle triangle = new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(1,2,1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: The point of intersection with the "contained" plane is inside the triangle.
        Ray ray = new Ray( new Vector(0, 0, 1),new Point(0.25, 0.25, -1));
        Point point = new Point(0.25, 0.25, -1);
        List<Point> result = triangle.findIntsersections(ray);
        assertEquals(1,result.size(),"Wrong number of points");
        assertEquals(point,result.get(0),"Wrong number of points");
        //TC02:The point of intersection with the "contained" plane is outside the triangle - facing one edge


    }

}