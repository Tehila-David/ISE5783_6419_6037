package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1,1,1);
        assertEquals(v1, v2.add(new Vector(0, 1, 2)));

        assertThrows(
                IllegalArgumentException.class,
                () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception"
        );


    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");

    }

    @Test
    void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(3.7416, v1.length(), 0.001, "ERROR: lengthSquared() wrong value");

    }

    @Test
    void testNormalize() {
    }
}