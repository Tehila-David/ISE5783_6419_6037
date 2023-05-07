package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
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

}
