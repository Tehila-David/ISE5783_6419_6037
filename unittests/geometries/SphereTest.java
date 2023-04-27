package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.Ray;

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

    @Test
    void testfindIntersections()
    {
        Sphere sphere = new Sphere(1, new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray( new Vector(1, 1, 0),new Point(-1, 0, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntsersections(new Ray
                (new Vector(3, 1, 0),new Point(-1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        result=sphere.findIntsersections(new Ray
                (new Vector(1.21, 4.47, 0),new Point(1, 0, 0)));
        Point p3 = new Point(1.26, 0.96, 0);
        assertEquals(1,result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts inside the sphere-wrong point");
        // TC04: Ray starts after the sphere (0 points)

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

    }

}