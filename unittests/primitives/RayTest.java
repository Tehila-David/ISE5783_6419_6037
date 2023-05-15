package primitives;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
public class RayTest {

    @Test
    void testFindClosestPoint()
    {
        Ray ray = new Ray(new Point(2,4,6), new Vector(4,10,2));
        Point a = new Point(6,14,8);
        Point b = new Point(10,24,10);
        Point c = new Point(30,74,20);
        List<Point> points;

        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for the point in the middle of the list is the one closest to the beginning of the fund
        points = new LinkedList<>();
        points.add(b);
        points.add(a);
        points.add(c);
        assertEquals(ray.findClosestPoint(points),a,
                "The point closest to the beginning of the ray is in the middle of the list - didn't return a correct point");

        // =============== Boundary Values Tests ==================
        //TC11: Test for an empty list
        points = null;
        assertNull(ray.findClosestPoint(points), "list of points is empty - didn't return null");

        //TC12: Test that the first point is closest to the beginning of the horn
        points = new LinkedList<>();
        points.add(a);
        points.add(b);
        points.add(c);
        assertEquals(ray.findClosestPoint(points),a,
                "The point closest to the beginning of the ray is in the first of the list - didn't return a correct point");


        //TC13: Test that the last point is closest to the beginning of the horn
        points = new LinkedList<>();
        points.add(b);
        points.add(c);
        points.add(a);
        assertEquals(ray.findClosestPoint(points),a,
                "The point closest to the beginning of the ray is in the end of the list - didn't return a correct point");

    }
}
