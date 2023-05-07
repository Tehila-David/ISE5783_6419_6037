package renderer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;
import geometries.Triangle;
import geometries.Sphere;
import geometries.Plane;
import geometries.Intersectable;
import java.util.List;


/*Integration tests between creating rays from a camera
 *and calculating sections of a ray with geometric bodies
 */
public class RaysConstructionTest
{
    /**
     * Integration tests of a camera and a plane.
     */
    @Test
    void testRayConstructionPlane() {
        Camera camera = new Camera(new Point(0, 0, 2), new Vector(0,0,-2), new Vector(0, 2, 0))
                .setVPDistance(2)
                .setVPSize(3, 3);

        // TC01: plane is before the camera and parallel to the view plane(9 points)
        Plane plane = new Plane(new Point(0, 0, -4), new Vector(0, 0, 2));
        assertEquals(9, numberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera and parallel to the view plane - wrong number of intersections");

        // TC02: plane is before the camera(6 points)
        plane = new Plane(new Point(3, 3, 0), new Vector(-2,0,2));
        assertEquals(6, numberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera - wrong number of intersections");

        // TC02: plane is before the camera(9 points)
        plane = new Plane(new Point(3, 3, 0), new Vector(-2,0,6));
        assertEquals(9, numberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera - wrong number of intersections");
    }

    /**
     * Integration tests of a camera and a sphere.
     */
    @Test
    void testRayConstructionSphere()
    {
        Camera camera = new Camera(new Point(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: Sphere before the camera  and is smaller than the view plane (2 points)
        Sphere sphere = new Sphere( 1,new Point(0, 0, -2.5));
        assertEquals(2, numberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and is smaller than the view plane  - wrong amount of intersections");

        // TC02: Sphere before the camera and bigger than the view plane (18 points)
        sphere = new Sphere( 2.5,new Point(0, 0, -2.5));
        assertEquals(18, numberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and bigger than the view plane  - wrong amount of intersections");

        // TC03: Sphere before the camera and smaller than the view plane (10 points)
        sphere = new Sphere( 2,new Point(0, 0, -2));
        assertEquals(10, numberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and smaller than the view plane  - wrong amount of intersections");

        // TC04: Sphere encapsulates the view plane (9 points)
        sphere = new Sphere(4,new Point(0, 0, -0.5));
        assertEquals(9, numberOfIntersection(camera, sphere, 3, 3),
                "Sphere encapsulates the view plane  - wrong amount of intersections");

        // TC05: Sphere after the camera and smaller than the view plane (0 points)
        sphere = new Sphere( 0.5,new Point(0, 0, 2));
        assertEquals(0, numberOfIntersection(camera, sphere, 3, 3),
                "Sphere after the camera and smaller than the view plane  - wrong amount of intersections");
    }

    /**
     * Integration tests of a camera and a triangle.
     */
    @Test
    void testRayConstructionTriangle() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0,0,-2), new Vector(0, 2, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: triangle is before the camera, parallel and smaller than the view plane (0 points)
        Triangle triangle = new Triangle(new Point(0, 2, -4), new Point(2, -2, -4), new Point(-2, -2, -4));
        assertEquals(1, numberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and smaller tha the view plane  - wrong number of intersections");

        // TC02: triangle is before the camera, parallel and bigger than the view plane (2 points)
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, numberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and bigger than the view plane  - wrong number of intersections");

    }






    /**
     *  Helper method for testing intersectables and a camera.
     *
     * @param camera the object to construct the rays from
     * @param intersectable the object to intersect with the rays
     * @param nX the width pixels of the view plane
     * @param nY the height pixels of the view plane
     * @return the amount of intersections with a geometry from a camera
     */
    int numberOfIntersection(Camera camera, Intersectable intersectable, int nX, int nY){
        int numberIntersections = 0;

        for (int i = 0 ; i < nX ; i++)      // go over all the pixels in the view plane
            for(int j = 0; j < nY; j++)     // for each pixel [j,i], construct a ray and
            {                               // check the intersection points with the geometry
                List<Point> pointList = intersectable.findIntersections(camera.constructRay(nX, nY, j, i));
                if (pointList != null)
                    numberIntersections += pointList.size();
            }
        return numberIntersections;
    }


}
