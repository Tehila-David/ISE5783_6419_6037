package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import lighting.PointLight;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene
{
    public String Name;  //The name of the scene
    public Color background;  //background color
    public AmbientLight ambientLight;  //Ambient lighting

    public Geometries geometries;   //The 3D model
     public List<LightSource> lights = new LinkedList<>();
    /**
    *set the lights
      * @param lights the list of LightSource
     * @return the lights . by builder pattern
    */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /*constructor of Scene*/
    public Scene(String name)
    {
        this.Name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
        this.geometries = new Geometries();
    }

    /*set to background color*/
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /*set to Ambien tLight*/
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /*set to Geometries*/
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    public Scene addGeometry(Intersectable geometry){
        geometries.add(geometry);
        return this;
    }
}
