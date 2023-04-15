
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Point class
 */
public class PointTest {


    @Test
    void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Point p1 = new Point(1, 2, 3);
        assertEquals(true,p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0)),"ERROR: Point - Point does not work correctly");
        assertEquals(true,p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0)),"ERROR: Point + Vector does not work correctly");
    }


    @Test
    void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals(v1.subtract(v2),new Vector(3, 6, 9),"ERROR: Point - Point does not work correctly");


    }


    @Test
    void testDistanceSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,3);
        assertEquals(2,p1.distanceSquared(new Point(1,3,4)));

    }


    @Test
    void testDistance()
    {
        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(1,2,0);
        assertEquals(1,p1.distanceSquared(new Point(1,3,0)));
    }

}