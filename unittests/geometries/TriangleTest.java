package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for GetNormal
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 3, 6);
        Point p3 = new Point(1, 1, 1);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector normal = new Vector(1.0, 0.0, -0.0);
        //TC01: Test if the method returns the normal
        assertEquals(normal, triangle.getNormal(new Point(1, 2, 2)), "ERROR: GetNormal() wrong value");
        //TC02: Test if the normal vector is equal to 1
        assertEquals(1, triangle.getNormal(new Point(1, 2, 2)).length(), "ERROR: length() wrong value");

    }

    @Test
    void testfindIntersections() {
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============
        // TC01:Ray intersects the triangle
        result = triangle.findIntersections(new Ray(new Point(-0.5, -0.5, -1.5), new Vector(1, 1, 2)));
        Point point = new Point(0.3750000000000002,0.3750000000000002,0.25000000000000044);
        assertEquals(1, result.size(), "Ray intersects the triangle-Wrong number of points");
        assertEquals(point, result.get(0), "Ray intersects the triangle- Wrong point");

        //TC02:Ray outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -2, -2), new Vector(1, 1, 2))), "Ray outside against vertex - not return null");

        //TC03: Ray outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -2, -2), new Vector(1, 1, 2))), "Ray  outside against edge - not return null");

        // ============ Boundary Values Tests =============
        //TC11: Ray On edge
        assertNull(triangle.findIntersections(new Ray(new Point(1.3, -1.78, -1.81), new Vector(-0.82, 1.78, 2.33))), "Ray On edge - not return null");
        //TC12: Ray in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(1.3, -1.78, -1.81), new Vector(-1.3, 1.78, 2.81))), "Ray On vertex- not return null");
        //TC13: Ray On edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(1.3, -1.78, -1.81), new Vector(-2.15, 1.78, 3.65))), "Ray On edge's continuation- not return null");

    }
}

