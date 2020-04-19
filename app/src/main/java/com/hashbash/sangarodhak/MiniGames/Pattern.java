package com.hashbash.sangarodhak.MiniGames;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Pattern extends PApplet {

    float r1 = 200;
    float r2 = 400;
    float x1, y1, pX, pY;

    int a = 500, b = 40;

    int red = 100, blue = 200, green = 0;

    boolean addRed = true, addBlue = true, addGreen = true;

    int changeColor = 5;

    float a1 = 0;
    float a2 = 0;

    float cX, cY;

    PGraphics pathDrawing;

    public void mousePressed() {
        //a2 += 0.4;
    }

    public void setup() {

        cX = width / 2;
        cY = height / 2;

        a = Math.min(height, width)/2;

        pathDrawing = createGraphics(width, height);
        pathDrawing.beginDraw();
        pathDrawing.background(0);
        pathDrawing.endDraw();
    }

    public void draw() {

        image(pathDrawing, 0, 0);

        translate(cX, cY);
        rotate(a2);

        //  r1 = 400*cos(a1 - a2) + 50*sin(a1 - a2) ;

        x1 = a * cos(1 * (a1-a2));
        y1 = b * sin(5 * (a1-a2));

        stroke(255);
        strokeWeight(1);
        line(0, 0, x1, y1);
        line(0, 0, y1, x1);

        a1 += PI / 50;
        a2 += PI / 10000;

        pathDrawing.beginDraw();
        pathDrawing.translate(cX, cY);
        pathDrawing.strokeWeight(2);
        pathDrawing.rotate(a2);
        pathDrawing.stroke(50 + (y1 >= 0 ? y1 : -y1));
//        pathDrawing.stroke(red, blue, green);
        if (frameCount > 1) {
            pathDrawing.line(pX, pY, x1, y1);
            pathDrawing.line(pY, pX, y1, x1);
        }

        pathDrawing.endDraw();

        adjustColor();

        //r2 = 200 + random(5) - random(5);

        pX = x1;
        pY = y1;
    }

    public void adjustColor() {

        if (addRed)
            red += random(changeColor);
        else
            red -= random(changeColor);

        if (addBlue)
            blue += random(changeColor);
        else
            blue -= random(changeColor);

        if (addGreen)
            green += random(changeColor);
        else
            green -= random(changeColor);

        if (red >= 255 - changeColor)
            addRed = false;
        else if (red <= 20 * changeColor)
            addRed = true;

        if (blue >= 255 - changeColor)
            addBlue = false;
        else if (red <= 20 * changeColor)
            addBlue = true;

        if (green >= 255 - changeColor)
            addGreen = false;
        else if (red <= 20 * changeColor)
            addGreen = true;
    }

    public void settings() {
        fullScreen();
    }
}
