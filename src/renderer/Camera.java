package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

/**
 * a class that represents a camera.
 */
public class Camera {

    /*Location of the camera*/
    private Point p0;
    /*A vector pointing up relative to the camera*/
    private Vector vUp;
    /*A vector pointing forward relative to the camera */
    private Vector vTo;
    /*A vector pointing to the right relative to the camera*/
    private Vector vRight;
    /*height of the view plane*/
    private double height;
    /*width of the view plane*/
    private double width;
    /*distance between the camera and the view plane*/
    private double distance;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /*constructor:
     * @throws IllegalArgumentException throws an exception if
     *         the reference vectors (vUp, vTo) are not orthogonal
     */
    public Camera(Point p0, Vector vTo , Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vectors vTo and vUp are not orthogonal");
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }


    /*set method for the size of the Plane View.
     *@param width the width of the VP
     *@param height the height of the VP
     *@return the camera itself. (builder pattern)
     * */
    public Camera setVPSize(double width, double height)
    {
        this.width = width;
        this.height = height;
        return this;
    }

    /*
    *set method for the Plane View distance from the camera.
    * @param distance the distance from the VP
    * @return the camera itself. (builder pattern)
    */
    public Camera setVPDistance(double distance)
    {
        this.distance=distance;
        return this;
    }


    /*
    *A method of creating a beam through the center of a pixel.
    * @param nX number of pixels on the width of the view plane.
    * @param nY number of pixels on the height of the view plane.
    * @param j location of the pixel in the X direction.
    * @param i location of the pixel in the Y direction.
    * @return the constructed ray - from p0 through the wanted pixel.
    */
    public Ray constructRay(int nX, int nY, int j, int i)
    {
        Point pointCenterVP = p0.add(vTo.scale(distance)); //center of the view plane
        double Ry = alignZero(height/nY);  //Ratio - pixel height
        double Rx = alignZero(width/nX);  //Ratio - pixel width

        double yI = alignZero(-(i - (nY - 1) / 2d) * Ry);   //move pointCenterVP Yi pixels
        double xJ = alignZero((j - (nX - 1) / 2d) * Rx);   //move pointCenterVP Xj pixels

        Point PointIJ = pointCenterVP;
        if(!isZero(xJ))
            PointIJ = PointIJ.add(vRight.scale(xJ));      //move the point in vRight direction
        if(!isZero(yI))
            PointIJ = PointIJ.add(vUp.scale(yI));        //move the point in vUp direction

        return new Ray(p0, PointIJ.subtract(p0));       //The ray through the wanted pixel
    }

    /*set method for ImageWriter
    */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    /*set method for RayTracer
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public void renderImage()
    {
        if(height==0||width==0||distance==0|| imageWriter==null||rayTracer==null)
            throw new MissingResourceException("Camera is missing some fields", "Camera", "field");

        int Nx = imageWriter.getNx();
        for (int i = 0; i < Nx; i++){
            int Ny = imageWriter.getNy();
            for (int j = 0; j < Ny; j++){
                Color color = castRay(Nx, Ny, i, j);
                imageWriter.writePixel(j, i, color); // find the color of the pixel and construction of a ray through the pixel
                // and intersecting with the geometries
            }
        }
    }

    private Color castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }

    /**
     *  print a grid on the image without running over the original image
     * @param interval the size of the grid squares
     * @param color the color of the grid
     * @throws MissingResourceException
     */

    public void printGrid(int interval, Color color)
    {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        for (int i = 0; i< imageWriter.getNy(); i+=interval)
            for(int j = 0; j< imageWriter.getNx(); j++)
                imageWriter.writePixel(j,i,color); // color the grid

        for (int i = 0; i< imageWriter.getNx(); i+=interval)
            for(int j = 0; j< imageWriter.getNy(); j++)
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




}
