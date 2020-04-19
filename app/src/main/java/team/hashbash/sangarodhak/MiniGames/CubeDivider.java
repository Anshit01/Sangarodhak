package team.hashbash.sangarodhak.MiniGames;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class CubeDivider extends PApplet {

    private ArrayList<Box> allBoxes;

    private float angleX = 0.001f, angleY = 0.01f, angleZ = 0.04f;

    public void setup() {



        allBoxes = new ArrayList<>();

        allBoxes.add(new Box(0, 0, 0, Math.min(height, width)/2 - 10));

    }

    public void touchStarted(){
            divideBoxes();
    }

    public void draw() {
        background(0);

        angleX += 0.01;
        angleY += 0.01;
        angleZ += 0.01;

        translate(width/2, height/2, 0);

        rotateX(angleX);
        rotateY(angleY);
        rotateZ(angleZ);

        noFill();
        stroke(255);

        lights();

        for (Box b : allBoxes){
            b.show();
        }
    }

    private void divideBoxes(){
        ArrayList<Box> nextGen = new ArrayList<Box>();
        for(Box b : allBoxes)
            nextGen.addAll(b.generate());
        allBoxes = nextGen;
    }
    class Box {

        PVector pos;
        float size;

        Box(float x, float y, float z, float size) {
            this.pos = new PVector(x, y, z);
            this.size = size;
        }

        ArrayList<Box> generate() {
            ArrayList<Box> generated = new ArrayList<Box>();
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        float newSize =  size/3;
                        int sum = abs(x) + abs(y) + abs(z);
                        if (sum > 1) {
                            Box b = new Box(pos.x + x*newSize, pos.y + y*newSize, pos.z + z*newSize, newSize);
                            generated.add(b);
                        }
                    }
                }
            }
            return generated;
        }

        void show() {
            pushMatrix();
            translate(pos.x, pos.y, pos.z);
            stroke(0);
            strokeWeight(1);
            fill(255);
            box(size);
            popMatrix();
        }
    }
    public void settings() {  fullScreen(P3D); }
}

