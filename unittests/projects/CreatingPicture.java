package projects;
import scene.Scene;
import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import static java.awt.Color.*;



public class CreatingPicture {

    private Scene scene = new Scene("Image --- scene");


    @Test
    void createImage() {

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-80, -80, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))); //

        scene.geometries.add( //
                // up left
                new Triangle(new Point(-80, 0, -80), new Point(0, 100, -100), new
                        Point(-100, 100, -100))
                        .setMaterial(new Material().setKd(1.5).setKs(0.5).setShininess(60)),
                // down left
                new Triangle(new Point(-100, 0, -50), new Point(0, -100, -100), new
                        Point(-100, -100, -100))
                        .setMaterial(new Material().setKd(1.5).setKs(0.5).setShininess(60)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
                        Point(100, -100, -100))
                        .setEmission(new Color(cyan)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("imageeeeeeeeeee", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();


    }


}
