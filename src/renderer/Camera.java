package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * a class that represents a camera.
 */
public class Camera
{

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    public Camera(Vector vUp ,Vector vTo )
    {
        this.vUp = vUp;
    }
    

}
