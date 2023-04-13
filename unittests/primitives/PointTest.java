
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 */
public class PointTest {

    /**
     * Test the add() method of the Point class.
     * Equivalence Partitions:
     * - Adding a vector to a point
     *
     * Boundary Values Tests:
     * - Adding a negative vector to a point should throw an IllegalArgumentException.
     */
    @Test
    void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,3);
        Point p2 =new Point(1,3,4);
        assertEquals(p2, p1.add(new Vector(0, 1, 1)));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> p1.add(new Vector(-1, -2, -3)),"ERROR: point + -itself does not throw an exception"
        );
    }

    /**
     * Test the subtract() method of the Point class.
     * Equivalence Partitions:
     * - Subtracting a vector from a point
     *
     * Boundary Values Tests:
     * - Subtracting the point from itself should throw an IllegalArgumentException.
     */
    @Test
    void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,3);
        Point p2 =new Point(1,0,0);
        assertEquals(p2, p1.subtract(new Vector(0, 2, 3)));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> p1.subtract(new Vector(1, 2, 3)),"ERROR: point -itself does not throw an exception"
        );
    }

    /**
     * Test the distanceSquared() method of the Point class.
     * Equivalence Partitions:
     * - Distance squared between two points
     *
     * Boundary Values Tests:
     * - Calculating the distance squared between a point and itself should throw an IllegalArgumentException.
     */
    @Test
    void testDistanceSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,3);
        assertEquals(2,p1.distanceSquared(new Point(1,3,4)));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> p1.distanceSquared(new Point(1, 2, 3)),"ERROR: point -itself does not throw an exception"
        );
    }

    /**
     * Test the distance() method of the Point class.
     * Equivalence Partitions:
     * - Distance between two points
     *
     * Boundary Values Tests:
     * - Calculating the distance between a point and itself should throw an IllegalArgumentException.
     */
    @Test
    void testDistance()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,0);
        assertEquals(1,p1.distanceSquared(new Point(1,3,0)));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> p1.distanceSquared(new Point(1, 2, 0)),"ERROR: point -itself does not throw an exception"
        );
    }

}