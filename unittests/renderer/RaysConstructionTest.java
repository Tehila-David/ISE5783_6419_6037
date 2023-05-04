package renderer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;
import geometries.Triangle;
import geometries.Plane;
import geometries.Sphere;
import geometries.Intersectable;
import java.util.List;


/*Integration tests between creating rays from a camera
 *and calculating sections of a ray with geometric bodies
 */
public class RaysConstructionTest
{
    /**
     * A test to create rays from a camera and
     * Calculation of intersections of a beam with a triangle
     */
    @Test
    void testRayConstructionTriangle() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0,0,-2), new Vector(0, 2, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: triangle is before the camera, parallel and smaller than the view plane (0 points)
        Triangle triangle = new Triangle(new Point(0, 2, -4), new Point(2, -2, -4), new Point(-2, -2, -4));
        assertEquals(1, getNumberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and smaller tha the view plane (0 points) - wrong number of intersections");

        // TC02: triangle is before the camera, parallel and bigger than the view plane (2 points)
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, getNumberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and bigger than the view plane (2 points) - wrong number of intersections");

    }






    /**
     *  help method for the tests
     *
     * @param camera the object to construct the rays from
     * @param intersectable the object to intersect with the rays
     * @param nX the width pixels of the view plane
     * @param nY the height pixels of the view plane
     * @return the amount of intersections with a geometry from a camera through a view plane
     */
    int getNumberOfIntersection(Camera camera, Intersectable intersectable, int nX, int nY){
        int numOfIntersections = 0;

        for (int i = 0 ; i < nX ; i++)      // go over all the pixels in the view plane
            for(int j = 0; j < nY; j++)     // for each pixel [j,i], construct a ray and
            {                               // check the intersection points with the geometry
                List<Point> l = intersectable.findIntersections(camera.constructRay(nX, nY, j, i));
                if (l != null)
                    numOfIntersections += l.size();
            }
        return numOfIntersections;
    }


}
