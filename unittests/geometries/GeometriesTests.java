package geometries;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import primitives.Vector;

public class GeometriesTests
{

    @Test
    void testFindIntersections()
    {
        Geometries geometries = new Geometries(
                 new Plane(new Point(2,2,1), new Vector(0, 0, 2)),
                 new Sphere(new Point(4,0,0), 4),
                 new Triangle(new Point(0, 2, 0), new Point(0, -2, 0), new Point(6,0,0)));


        // ============ Equivalence Partitions Tests ==============

        // TC01: A few geometries intersects - Ray intersects only sphere and plane (3 points)
        Ray ray = new Ray(new Point(2,2,-4), new Vector(0,0,2));
        assertEquals(3, geometries.findIntersections(ray).size(), "Ray intersects only sphere and plane (3 points)- wrong points of intersection");


        // =============== Boundary Values Tests ==================

        // TC02: Ray intersects all the geometries (4 points)
        ray = new Ray(new Point(1, 1, -4), new Vector(0, 0, 2));
        assertEquals(4, geometries.findIntersections(ray).size(), "Ray intersects all the geometries (4 points) - wrong points of intersection");

        // TC03: Ray intersects one the geometries (1 point)
        ray = new Ray(new Point(10, 0, -4), new Vector(0, 0, 2));
        assertEquals(1, geometries.findIntersections(ray).size(), "Ray intersects one the geometries (1 points) - wrong points of intersection");

        // TC04:  No geometries intersects (0 points)
        ray = new Ray(new Point(5,-11,-4), new Vector(13,21,-6));
        assertNull(geometries.findIntersections(ray),
                "Ray doesn't intersect any geometries (0 points) - found a point of intersection");

        // TC05: Empty list of geometries
        geometries = new Geometries();
        assertNull(geometries.findIntersections(ray), "Empty collection of geometries - found an intersection");

    }


}
