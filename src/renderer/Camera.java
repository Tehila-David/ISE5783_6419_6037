package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class that represents a camera.
 */
public class Camera {

    /**
     * Location of the camera
     */
    private Point location;
    /**
     * A vector pointing up relative to the camera
     */
    private Vector vUp;
    /**
     * A vector pointing forward relative to the camera
     */
    private Vector vTo;
    /**
     * A vector pointing to the right relative to the camera
     */
    private Vector vRight;
    /**
     * height of the view plane
     */
    private double height;
    /**
     * width of the view plane
     */
    private double width;
    /**
     * distance between the camera and the view plane
     */
    private double distance;
    /**
     * Object of Image Writer
     */
    private ImageWriter imageWriter;
    /**
     * Object of RayTracerBase
     */
    private RayTracerBase rayTracer;
    /**
     * Image improvement - Number of rays in the beam for  AntiAliasing
     */
    private int raysBeamAntiAliasing = 1;
    /**
     * Time Improvement for super-sampling
     */
    private boolean adaptiveSuperSampling = false;
    /**
     * Time improvement by Threads
     */
    private boolean multiThreading = false;
    /**
     * Num of threads
     */
    private int threadsCount = 3;

    /**
     * Constructor of Camera
     *
     * @param p0  location of Camera
     * @param vTo Vector forward relative to the camera
     * @param vUp Vector up relative to the camera
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vectors vTo and vUp are not orthogonal");
        this.location = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }

    /**
     * @return Location of camera
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return Vector of Up
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * @return Vector of To
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * @return Vector of Right
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * @return Height of view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return Width of view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return Distance of between the camera and the view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * find the color that specific ray encounter.
     *
     * @param ray ray in the space.
     * @return the color that the ray encounter.
     */
    private Color castRay1(Ray ray) {
        return rayTracer.traceRay(ray);
    }


    /*set method for the size of the  View Plane.
     *@param width the width of the VP
     *@param height the height of the VP
     *@return the camera itself. (builder pattern)
     * */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /*
     *set method for the View Plane distance from the camera.
     * @param distance the distance from the VP
     * @return  Camera
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * function that sets the num rays of  raysBeamAntiAliasing
     *
     * @param raysBeamAntiAliasing value to set
     * @return the camera
     */
    public Camera setRaysBeamAntiAliasing(int raysBeamAntiAliasing) {
        this.raysBeamAntiAliasing = raysBeamAntiAliasing;
        return this;
    }


    /**
     * start the anti aliasing improvement.
     *
     * @param numRays  numRays * numRays is the number of rays through a pixel
     * @param adaptive Time Improvement for super-sampling
     * @return Camera
     */
    public Camera antiAliasingStart(int numRays, boolean adaptive) {
        if (numRays < 1) {
            throw new IllegalArgumentException("ERROR! The number of rays must be at least 1 ");
        }
        this.raysBeamAntiAliasing = numRays;
        this.adaptiveSuperSampling = adaptive;
        return this;
    }

    /**
     * stop the anti aliasing improvement
     *
     * @return Camera
     */
    public Camera antiAliasingStop() {
        this.raysBeamAntiAliasing = 1;
        return this;
    }


    /**
     * start the multi threading improvement
     *
     * @return Camera
     */
    public Camera multiThreadingStart() {
        this.multiThreading = true;
        return this;
    }

    /**
     * stop the multi threading improvement.
     *
     * @return Camera
     */
    public Camera multiThreadingStop() {
        this.multiThreading = false;
        return this;
    }

    /**
     * A function that produces the image
     *
     * @return Camera
     */
    public Camera renderImage() {
        if (height == 0 || width == 0 || distance == 0 || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("ERROR! Camera is missing some fields", "Camera", "field");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        //call the appropriate function if it with multi threading.
        if (multiThreading) {
            return renderImageMultiThreading();
        }
        //go over the pixels and find the color of each pixel.
        IntStream.range(0, nX).parallel().forEach(i -> {
            IntStream.range(0, nY).parallel().forEach(j -> {
                Color color;
                //If there is no image improvement
                if (raysBeamAntiAliasing == 1) {
                    color = castRay(nX, nY, i, j);
                }
                //If there is image improvement and time improvement - raysBeamAntiAliasing>1 && adaptiveSuperSampling==True
                else if (adaptiveSuperSampling) {
                    color = pixelColorAdaptive(nX, nY, j, i);
                }
                //If there is only image improvement- raysBeamAntiAliasing>1 && adaptiveSuperSampling==False
                else {
                    List<Ray> rays = constructRaySuperSampling(nX, nY, j, i);
                    color = Color.BLACK;
                    for (Ray ray : rays) {
                        color = color.add(castRay1(ray));
                    }
                    color = color.reduce(rays.size());
                }
                imageWriter.writePixel(j, i, color);
            });
        });
        return this;
    }

    /**
     * A function that produces the image by MultiThreading
     * @return Camera
     */
    public Camera renderImageMultiThreading() {
        if (imageWriter == null || rayTracer == null || width == 0 || height == 0 || distance == 0) {
            throw new MissingResourceException("Camera is missing some fields", "Camera", "field");
        }

        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();
        Pixel.initialize(nY, nX, 1);
        while (threadsCount-- > 0) {
            new Thread(() -> {          // start the treads
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                    Color color = Color.BLACK;
                    //If there is no image improvement
                    if (raysBeamAntiAliasing == 1) {
                        color = castRay(nX, nY, pixel.col, pixel.row);
                    }
                    //If there is image improvement and time improvement - raysBeamAntiAliasing>1 && adaptiveSuperSampling==True
                    else if (adaptiveSuperSampling) {
                        color = pixelColorAdaptive(nX, nY, pixel.col, pixel.row);
                    }
                    //If there is only image improvement- raysBeamAntiAliasing>1 && adaptiveSuperSampling==False
                    else {
                        List<Ray> rays = constructRaySuperSampling(nX, nY, pixel.col, pixel.row);
                        color = Color.BLACK;
                        for (Ray ray : rays) {
                            color = color.add(castRay1(ray));
                        }
                        color = color.reduce(rays.size());
                    }
                    imageWriter.writePixel(pixel.col, pixel.row, color);
                }

            }).start();
        }
        Pixel.waitToFinish();
        return this;
    }


    /**
     * find the rays that go through the specific pixel in the view plane.
     *
     * @param nX the amount of columns of pixels.
     * @param nY the amount of rows of pixels.
     * @param j  the specific column of the wanted pixel.
     * @param i  the specific row of the wanted pixel.
     * @return the ray that go through the pixel.
     */
    public List<Ray> constructRaySuperSampling(int nX, int nY, int j, int i) {
        List<Ray> raysBeam = new LinkedList<Ray>();

        Point planeCenter = location.add(vTo.scale(distance));
        //ratio- pixel height and width
        double pixelHeight = (double) height / nY;
        double pixelWidth = (double) width / nX;
        double cellHeight = (double) pixelHeight / raysBeamAntiAliasing;
        double cellWidth = (double) pixelWidth / raysBeamAntiAliasing;

        //pixel[i,j] center
        Point Pij = planeCenter;
        Point point;
        //how to move from the center of the view plane.
        double Yi = -(0.5 + i - (nY - 1) / 2d) * pixelHeight + 0.5 * cellHeight;
        double Xj = (-0.5 + j - (nX - 1) / 2d) * pixelWidth + 0.5 * cellWidth;
        if (Xj != 0) Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0) Pij = Pij.add(vUp.scale(Yi));
        for (int k = 0; k < raysBeamAntiAliasing; k++) {
            for (int l = 0; l < raysBeamAntiAliasing; l++) {
                point = Pij;
                if (k != 0) point = point.add(vRight.scale(cellWidth * k));
                if (l != 0) point = point.add(vUp.scale(cellHeight * l));
                raysBeam.add(new Ray(location, point.subtract(location)));
            }
        }
        return raysBeam;
    }

    /**
     * find the rays that go through the specific pixel in the view plane.
     *
     * @param nX the amount of columns of pixels.
     * @param nY the amount of rows of pixels.
     * @param j  the specific column of the wanted pixel.
     * @param i  the specific row of the wanted pixel.
     * @return the ray that go through the pixel.
     */
    public Color pixelColorAdaptive(int nX, int nY, int j, int i) {
        //view plane center
        Point planeCenter = location.add(vTo.scale(distance));
        //ratio- pixel height and width
        double pixelHeight = (double) height / nY;
        double pixelWidth = (double) width / nX;
        double cellHeight = (double) pixelHeight / raysBeamAntiAliasing;
        double cellWidth = (double) pixelWidth / raysBeamAntiAliasing;

        //pixel[i,j] center
        Point Pij = planeCenter;
        Point point;
        double Yi = -(0.5 + i - (nY - 1) / 2d) * pixelHeight + 0.5 * cellHeight; //how to move from the center of the view plane.
        double Xj = (-0.5 + j - (nX - 1) / 2d) * pixelWidth + 0.5 * cellWidth;
        if (Xj != 0) Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0) Pij = Pij.add(vUp.scale(Yi));
        List<Color> colors = List.of(  //find the colors of the corners of the pixel
                castRay1(new Ray(this.location, Pij.subtract(this.location))), castRay1(new Ray(this.location, Pij.add(vUp.scale(pixelHeight)).subtract(this.location))), castRay1(new Ray(this.location, Pij.add(vUp.scale(pixelHeight)).add(vRight.scale(pixelWidth)).subtract(this.location))), castRay1(new Ray(this.location, Pij.add(vRight.scale(pixelWidth)).subtract(this.location))));
        int recursionLevel = (int) (Math.log(raysBeamAntiAliasing - 1) / Math.log(2)); // = log2(antiAliasingNumRays-1)
        return recursiveConstructRay(Pij, colors, pixelHeight, pixelWidth, vUp, vRight, recursionLevel);//***
    }

    /**
     * calculate the color of area with adaptive super sampling - calculate the average of recursions.
     *
     * @param corner the left down corner of the area.
     * @param c      the colors of the 4 corners, arranged clockwise. 0 if the left down, 1 if the left up, 2 if the right up, 3 if the right down.
     * @param height the height of the area.
     * @param width  the width of the area.
     * @param vUp    the vector of the direction up.
     * @param vRight the vector of the direction right.
     * @param level  the level of the recursion (we stop at level 0)
     * @return the color of the area (average of the colors that returned from the recursions.
     */
    public Color recursiveConstructRay(Point corner, List<Color> c, double height, double width, Vector vUp, Vector vRight, int level) {
        //if the colors are very similar return this color.
        if (c.get(0).equals(c.get(1)) && c.get(0).equals(c.get(2)) && c.get(0).equals(c.get(3)))
            return c.get(0);//stop the recursion

        if (level <= 0) //stop the recursion
        {
            return c.get(0).add(c.get(1)).add(c.get(2)).add(c.get(3)).reduce(4);
        }
        // 01 if the point (and the color) between the point p0 and p1 (for example).
        //Finds 3 internal points to be sent to the recursive calls.
        Point p01 = corner.add(vUp.scale(height / 2));
        Point p31 = corner.add(vRight.scale(width / 2));
        Point pCenter = corner.add(vUp.scale(height / 2)).add(vRight.scale(width / 2));
        //find the colors of all the internal points.
        Color c01 = castRay1(new Ray(this.location, p01.subtract(this.location)));
        Color c12 = castRay1(new Ray(this.location, corner.add(vUp.scale(height)).add(vRight.scale(width / 2)).subtract(this.location)));
        Color c23 = castRay1(new Ray(this.location, corner.add(vUp.scale(height / 2)).add(vRight.scale(width)).subtract(this.location)));
        Color c31 = castRay1(new Ray(this.location, p31.subtract(this.location)));
        Color cCenter = castRay1(new Ray(this.location, pCenter.subtract(this.location)));
        //call the recursions and calculate the average.
        return recursiveConstructRay(corner, List.of(c.get(0), c01, cCenter, c31), height / 2, width / 2, vUp, vRight, level - 1)
                .add(recursiveConstructRay(p01, List.of(c01, c.get(1), c12, cCenter), height / 2, width / 2, vUp, vRight, level - 1))
                .add(recursiveConstructRay(pCenter, List.of(cCenter, c12, c.get(2), c23), height / 2, width / 2, vUp, vRight, level - 1))
                .add(recursiveConstructRay(p31, List.of(c31, cCenter, c23, c.get(3)), height / 2, width / 2, vUp, vRight, level - 1)).reduce(4);
    }


    /*
     *A method of creating a beam through the center of a pixel.
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     * @param j location of the pixel in the X direction.
     * @param i location of the pixel in the Y direction.
     * @return the constructed ray - from p0 through the wanted pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pointCenterVP = location.add(vTo.scale(distance)); //center of the view plane
        double Ry = alignZero(height / nY);  //Ratio - pixel height
        double Rx = alignZero(width / nX);  //Ratio - pixel width

        double yI = alignZero(-(i - (nY - 1) / 2d) * Ry);   //move pointCenterVP Yi pixels
        double xJ = alignZero((j - (nX - 1) / 2d) * Rx);   //move pointCenterVP Xj pixels

        Point PointIJ = pointCenterVP;
        if (!isZero(xJ)) PointIJ = PointIJ.add(vRight.scale(xJ));      //move the point in vRight direction
        if (!isZero(yI)) PointIJ = PointIJ.add(vUp.scale(yI));        //move the point in vUp direction

        return new Ray(location, PointIJ.subtract(location));       //The ray through the wanted pixel
    }


    /**
     * set method for ImageWriter
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * set method for RayTracer
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Return the color of specific ray
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     * @param i location of the pixel in the X direction.
     * @param j location of the pixel in the Y direction.
     * @return Color
     */
    private Color castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }

    /**
     * print a grid on the image without running over the original image
     * @param interval the size of the grid squares
     * @param color    the color of the grid
     * @throws MissingResourceException
     */

    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        for (int i = 0; i < imageWriter.getNy(); i += interval)
            for (int j = 0; j < imageWriter.getNx(); j++)
                imageWriter.writePixel(j, i, color); // color the grid

        for (int i = 0; i < imageWriter.getNx(); i += interval)
            for (int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, color); // color the grid
    }

    /**
     * create the image file using the image writer
     */
    public void writeToImage() {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        imageWriter.writeToImage();
    }


    //TODO: BONUS - rotate the camera , stage 4

    /**
     * rotate the camera around the vUp vector -
     * rotate the camera to the left = move the scene to the right
     * rotate the camera to the right = move the scene to the left
     *
     * @param location: loaction of camera
     * @param to:       angle of the rotation
     * @return Camera with new position
     */
    public Camera moveRightLeftCamera(Point location, Point to) {
        Vector vec;
        try {
            vec = to.subtract(location);
        } catch (IllegalArgumentException ignore) {
            throw new IllegalArgumentException("ERROR! The camera cannot point at its starting location");
        }
        this.location = location;
        this.vTo = vec.normalize();
        this.vUp = (vTo.crossProduct(Vector.Z)).crossProduct(vTo).normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * rotate the camera around the vRight vector
     * rotate the camera upward = move the scene downward
     * rotate the camera downward = move the scene upward
     *
     * @param location: loaction of camera
     * @param to:       the angle of the rotation
     * @return Camera with new position
     */
    public Camera moveUpDownCamera(Point location, Point to) {
        Vector vec;
        try {
            vec = to.subtract(location);
        } catch (IllegalArgumentException ignore) {
            throw new IllegalArgumentException("ERROR! The camera cannot point at its starting location");
        }
        this.location = location;
        this.vTo = vec.normalize();
        this.vUp = (vTo.crossProduct(Vector.Y)).crossProduct(vTo).normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * move the reference point of the camera using the amount of units to move in the direction of each reference vector
     *
     * @param moveVUp    on the direction of vUp
     * @param moveVTo    on the direction of vTo
     * @param moveVRight on the direction of vRight
     * @return the camera itself. builder pattern
     */
    public Camera moveReferencePoint(double moveVUp, double moveVTo, double moveVRight) {
        if (!isZero(moveVUp)) this.location = this.location.add(vUp.scale(moveVUp));
        if (!isZero(moveVRight)) this.location = this.location.add(vRight.scale(moveVRight));
        if (!isZero(moveVTo)) this.location = this.location.add(vTo.scale(moveVTo));
        return this;
    }


}
