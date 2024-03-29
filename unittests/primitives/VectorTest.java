package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(0, 3, -2);
    Vector v3 = new Vector(-2, -4, -6);


    /**
     * Test method for Add
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test that checks if the operation of connecting vectors is correct
        Vector v4 = new Vector(1, 1, 1);
        assertEquals(v1, v4.add(new Vector(0, 1, 2)));

        // =============== Boundary Values Tests ==================
        //TC11:A test that checks if the addition of 2 vectors is equal to the zero vector.
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception"
        );


    }

    /**
     * Test method for Scale
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:A test that checks whether the multiplication of a vector by a scalar is correct
        assertEquals(v3, v1.scale(-2), "ERROR: testScale() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11:A test that checks the multiplication of a vector by a zero scalar
        assertThrows(IllegalArgumentException.class,()-> v1.scale(0), "ERROR: Scale by 0 must throw exception");
    }

    /**
     * Test method for DotProduct
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC02:The test checks whether the scalar product is correct
        assertEquals(-28, v1.dotProduct(v3), "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        //TC11:A test that checks whether a scalar product between 2 perpendicular vectors is equal to zero
        assertTrue(isZero(v1.dotProduct(v2)),"ERROR: dotProduct() for orthogonal vectors is not zero");

    }

    /**
     * Test method for CrossProduct
     */
    @Test
    void testCrossProduct()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v2);
        //TC01:Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(vr.length(),v1.length() * v2.length(),0.0001 ,"ERROR: crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }


    /**
     * Test method for LengthSquared
     */
    @Test
    void testLengthSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01:A test that checks that the squared length of the vector is correct
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");

    }

    /**
     * Test method for Length
     */
    @Test
    void testLength()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01:A test that checks that the length of the vector is correct
        Vector v1 = new Vector(0, 3, 4);
        assertEquals(5, v1.length(), 0.001, "ERROR: length() wrong value");

    }

    /**
     * Test method for Normalize
     */
    @Test
    void testNormalize()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        //TC01:A test that checks that the vector is normalized by checking that the length of the vector is equal to 1.
        assertEquals(1,u.length(),"ERROR: the normalized vector is not a unit vector");
        //TC02: test zero vector from cross-productof co-lined vectors
        assertThrows(Exception.class,
                () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

    }

}