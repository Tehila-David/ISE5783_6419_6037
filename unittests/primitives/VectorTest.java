package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest
{

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(0,3,-2);
    Vector v3 = new Vector(-2, -4, -6);
    @Test
    void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v4 = new Vector(1,1,1);
        assertEquals(v1, v4.add(new Vector(0, 1, 2)));
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
        assertEquals(v3,v1.scale(-2),"ERROR: testScale() wrong value");

    }

    @Test
    void testDotProduct()
    {
        // ============ Equivalence Partitions Tests ==============

        assertEquals(0, v1.dotProduct(v2),"ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(0,v1.dotProduct(v3) + 28,"ERROR: dotProduct() wrong value");

    }

    @Test
        void testCrossProduct()
        {
            // ============ Equivalence Partitions Tests ==============
            Vector vr = v1.crossProduct(v2);
            assertEquals(0, (vr.length())-(v1.length() * v2.length()),0.777 ,"ERROR: crossProduct() wrong result length");
            assertEquals(true, vr.dotProduct(v1)==0||vr.dotProduct(v2)==1,"ERROR: crossProduct() result is not orthogonal to its operands");
            assertThrows(
                    Exception.class,
                    () -> v1.crossProduct(v3),
                "ERROR: crossProduct() for parallel vectors does not throw an exception"
            );

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    @Test
    void testLengthSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");

    }

    @Test
    void testLength()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(3.7416, v1.length(), 0.001, "ERROR: length() wrong value");

    }

    @Test
    void testNormalize()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        assertEquals(0,u.length() - 1,"ERROR: the normalized vector is not a unit vector");
        assertThrows(Exception.class,
                () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        assertEquals(false,v.dotProduct(u) < 0,"ERROR: the normalized vector is opposite to the original one");

    }

}