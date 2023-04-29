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
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere - not return null");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray
                (new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Ray starts before and crosses the sphere-Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result=sphere.findIntersections(new Ray
                (new Point(1, 0, 0), new Vector(1.21, 4.47, 0)));
        Point p3 = new Point(1.2612897329616635,0.9652604184616826,0.0);
        assertEquals(1,result.size(), "Ray starts inside the sphere-Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts inside the sphere-wrong point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(4, 0, 0), new Vector(-0.2, 3.56, 0))),
                "Ray starts after the sphere ");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        result = sphere.findIntersections(new Ray(new Point(0.2, 0,0.6), new Vector(0.8, 0,0.4)));
        p3 = new Point(1,0,1);
        assertEquals( 1,result.size(),"Ray starts at sphere and goes inside- wrong amount of points" );
        assertEquals( p3, result.get(0), " Ray starts at sphere and goes inside- wrong point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0.42, -0.48,0.66), new Vector(-0.16, -3.3,-0.66))),"Ray starts at sphere and goes outside- not return null" );

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(1.0396513324082663,0.999213576688311,0.0);
        p2 = new Point(0.9603486675917337,-0.999213576688311,0.0);
        result = sphere.findIntersections(new Ray(new Point(0.85, -3.78, 0), new Vector(0.15,3.78,0)));
        assertEquals( 2, result.size(), "Ray starts before the sphere- wrong number of points");
        if (result.get(0).getY() < result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray starts before the sphere- wrong points");

        // TC14: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,1,0)));
        p3 = new Point(1,1,0);
        assertEquals( 1,result.size(),"Ray starts at sphere and goes inside- wrong amount of points" );
        assertEquals( p3,result.get(0), "Ray starts at sphere and goes inside- wrong point");

        // TC15: Ray starts inside (1 point)
        result= sphere.findIntersections(new Ray( new Point(1,-0.5,0),new Vector(0,0.5,0)));
        p3=new Point(1,1,0);
        assertEquals(1, result.size(), "Ray starts inside sphere -Wrong number of points");
        assertEquals(p3,result.get(0),"Ray starts inside sphere -wrong point");

        // TC16: Ray starts at the center (1 point)
        result=sphere.findIntersections(new Ray(new Point(1,0,0),new Vector(-1,0,0)));
        p3=new Point(0,0,0);
        assertEquals(1, result.size(), "Ray starts at the sphere's center- Wrong number of points");
        assertEquals(p3, result.get(0),"Ray starts at the sphere's center-wrong point");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1,0),new Vector(0,-1,0))),"Ray starts at sphere and goes outside- not return null");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1.5,0),new Vector(0,-1.5,0))),"Ray starts after sphere- not return null");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0),new Vector(1,0,0))),"Ray starts before the tangent point-not return null");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1,0), new Vector(1,0,0))),"Ray starts at the tangent point- not return null" );

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,2,1), new Vector(0,1,0))),"Ray starts at the tangent point- not return null" );
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(0,0,1))),"Ray's line is outside, ray is orthogonal to ray start to sphere's center line- not return null" );


    }
}




