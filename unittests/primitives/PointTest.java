
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 */
public class PointTest {

    /**
     * Test method for Add
     */
    @Test
    void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: A test that checks that the connection of the points is correct
        Vector v3 = new Vector(1, 2, 3);
        Point p2 = new Point(1, 2, 3);
        assertEquals(new Point(2,4,6),p2.add(v3),"ERROR: Connection of two vectors is incorrect");
    }

    /**
     * Test method for Subtract
     */
    @Test
    void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: A test that checks that the subtraction of the points is correct
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals(v1.subtract(v2),new Vector(3, 6, 9),"ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11:A test that tests subtraction from the same point
        assertThrows(IllegalArgumentException.class,()->v1.subtract(v1),
                "Subtracting a point from a proper point must throw an exception");

    }

    /**
     * Test method for DistanceSquared
     */
    @Test
    void testDistanceSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: A test that checks that the squared distance between the points is correct
        Point p1 = new Point(1,2,3);
        assertEquals(2,p1.distanceSquared(new Point(1,3,4)));

        // ============ Equivalence Partitions Tests ==============
        //TC11: Test distanceSquared with the same point
        assertEquals(0,p1.distance(p1),0.00001,"worng distanceSquared between the point and itself");
    }

    /**
     * Test method for Distance
     */
    @Test
    void testDistance()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: A test that checks that the distance between the points is correct
        Point p1 = new Point(1,2,0);
        assertEquals(1,p1.distance(new Point(1,3,0)));

        // ============ Equivalence Partitions Tests ==============
        //TC11: Test distance with the same point
        assertEquals(0,p1.distance(p1),0.00001,"worng distance between the point and itself");
    }

}