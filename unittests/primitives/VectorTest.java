package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for primitives.Vector class
 * @author Yossi Cohen
 */
class VectorTest {

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1,1,1);
        assertEquals(v1, v2.add(new Vector(0, 1, 2)));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception"
        );


    }

    @Test
    void testScale()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v1.scale(2));
        assertEquals(new Vector(0, 0, 0), v1.scale(0));

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.scale(-1),
                "ERROR: Scaling by a negative factor does not throw an exception"
        );
    }

    @Test
    void testDotProduct()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 0, -1);
        Vector v2 = new Vector(1,1,1);
        assertEquals(0, v1.dotProduct(v2),"ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(3, v1.dotProduct(v1), "ERROR: dotProduct() for identical vectors is not the square of the length");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 0, 0);
        assertEquals(0, v1.dotProduct(v3), "ERROR: dotProduct() with zero vector is not zero");
    }



    @Test
    void testCrossProduct()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0,1,0);
        assertEquals(new Vector(0, 0, 1), v1.crossProduct(v2));

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 0, 0);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "ERROR: crossProduct() with zero vector does not throw an exception"
        );
    }

    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");
        assertEquals(0, new Vector(0, 0, 0).lengthSquared(), "ERROR: lengthSquared() with zero vector is not zero");
    }

    @Test
    void testLength() {
// =    =========== Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(3.7416, v1.length(), 0.001, "ERROR: length() wrong value");
        assertEquals(0, new Vector(0, 0, 0).length(), "ERROR: length() with zero vector is not zero");
    }

    @Test
    void testNormalize()
    {
    }
    ////
}