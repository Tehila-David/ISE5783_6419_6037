package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

public interface Intersectable
{
    List<Point> findIntsersections(Ray ray);
    
}
