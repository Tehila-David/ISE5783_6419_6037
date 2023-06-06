package projects;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.BLACK;

public class DiamondStage7 {

    private Scene scene = new Scene("Test scene");

    @Test
    void createImage() {

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(80, 80).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        Point A = new Point(0, 0, 20);
        //vertices on the under side
        Point B1 = new Point(10, 0, 30);
        Point B2 = new Point(5, 8.7, 30);
        Point B3 = new Point(-5, 8.7, 30);
        Point B4 = new Point(-10, 0, 30);
        Point B5 = new Point(-5, -8.7, 30);
        Point B6 = new Point(5, -8.7, 30);
        //mid vertices on the underside
        Point B1B2 = new Point(7.5, 4.3, 30);
        Point B2B3 = new Point(0, 8.7, 30);
        Point B3B4 = new Point(-7.5, 4.3, 30);
        Point B4B5 = new Point(-7.5, -4.3, 30);
        Point B5B6 = new Point(0, -8.7, 30);
        Point B6B1 = new Point(7.5, -4.3, 30);
        //vertices on the upper side
        Point C1 = new Point(5, 0, 3.5);
        Point C2 = new Point(2.5, 4.3, 3.5);
        Point C3 = new Point(-2.5, 4.3, 3.5);
        Point C4 = new Point(-5, 0, 3.5);
        Point C5 = new Point(-2.5, -4.3, 3.5);
        Point C6 = new Point(2.5, -4.3, 3.5);
        //mid vertices on the upper side
        Point C1C2 = new Point(3.8, 2.2, 35);
        Point C2C3 = new Point(0, 4.3, 35);
        Point C3C4 = new Point(-3.8, 2.2, 35);
        Point C4C5 = new Point(-3.8, -2.2, 35);
        Point C5C6 = new Point(0, -4.3, 35);
        Point C6C1 = new Point(3.8, -2.2, 35);
        scene.geometries.add(
                //triangles on the bottom side
                new Triangle(A,B1,B1B2).setEmission(new Color(PINK)).setMaterial(new Material().setKs(0.8).setShininess(40)),
                new Triangle(A,B2,B1B2).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(A,B2,B2B3).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(0)),
                new Triangle(A,B3,B2B3).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(A,B3,B3B4).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(A,B4,B3B4).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(30)),

                new Triangle(A,B4,B4B5).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(A,B5,B4B5).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(A,B5,B5B6).setEmission(new Color(PINK)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(A,B6,B5B6).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(A,B6,B6B1).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(A,B1,B6B1).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                //triangles on the top side
                new Triangle(B1,C1,B1B2).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8)),
                new Triangle(C1,B1B2,C2).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B1B2,C2,B2).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(B2,C2,B2B3).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(C2,B2B3,C3).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B2B3,C3,B3).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(B3,C3,B3B4).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(C3,B3B4,C4).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B3B4,C4,B4).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(B4,C4,B4B5).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(C4,B4B5,C5).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B4B5,C5,B5).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(B5,C5,B5B6).setEmission(new Color(PINK)).setMaterial(new Material().setKs(0.8)),
                new Triangle(C5,B5B6,C6).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B5B6,C6,B6).setEmission(new Color(PINK)).setMaterial(new Material().setKs(0.8).setShininess(100)),

                new Triangle(B6,C6,B6B1).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(C6,B6B1,C1).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100)),
                new Triangle(B6B1,C1,B1).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.8).setShininess(100))
        );
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, 1)) //
                .setKl(4E-5).setKq(2E-7));
        camera.setImageWriter(new ImageWriter("DiamondStage7", 1000, 1000)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}
