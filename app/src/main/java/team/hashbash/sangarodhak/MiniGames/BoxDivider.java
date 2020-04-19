package team.hashbash.sangarodhak.MiniGames;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class BoxDivider extends PApplet {

    private ArrayList<Box> allBoxes;

    public void setup() {
        allBoxes = new ArrayList<Box>();
        allBoxes.add(new Box(0, 0, width, height));
    }

    @Override
    public void touchStarted() {
        super.touchStarted();

        ArrayList<Box> newGen;
        for (int i = 0; i < allBoxes.size(); i++) {
            if (mouseX < (allBoxes.get(i).x + allBoxes.get(i).boxWidth) && mouseY < (allBoxes.get(i).y + allBoxes.get(i).boxHeight)
                    && mouseX > allBoxes.get(i).x && mouseY > allBoxes.get(i).y) {
                newGen = allBoxes.get(i).generate();
                allBoxes.remove(i);
                allBoxes.addAll(newGen);
                break;
            }
        }

    }

    public void draw() {
        textSize(30);

        for (Box b : allBoxes)
            b.show();
    }

    public void settings() {
        fullScreen();
    }

    class Box {

        int x, y, boxWidth, boxHeight;

        PVector pv;

        Box(int x, int y, int boxWidth, int boxHeight) {
            this.x = x;
            this.y = y;
            this.boxWidth = boxWidth;
            this.boxHeight = boxHeight;
            pv = new PVector(random(255), random(255), random(255));
        }

        ArrayList<Box> generate() {
            ArrayList<Box> list = new ArrayList<Box>();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int newHeight = boxHeight / 2;
                    int newWidth = boxWidth / 2;
                    list.add(new Box(x + i * newWidth, y + j * newHeight, newWidth, newHeight));
                }
            }
            return list;
        }

        void show() {
            stroke(255);
            strokeWeight(2);
            fill(pv.x, pv.y, pv.z);
            rect(x, y, boxWidth, boxHeight);
            //image(img , x, y, boxWidth, boxHeight);
        }
    }
}
