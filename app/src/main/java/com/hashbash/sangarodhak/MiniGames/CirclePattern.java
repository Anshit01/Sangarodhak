package com.hashbash.sangarodhak.MiniGames;

import processing.core.PApplet;

public class CirclePattern extends PApplet {

    public void setup()
    {
        background(255);
    }

    public void draw()
    {
        fill(random(255),random(255),random(255));
        variableEllipse(mouseX, mouseY, pmouseX, pmouseY);
    }

    public void variableEllipse(int x, int y, int px, int py)
    {
        float speed = abs(x-px) + abs(y-py);
        stroke(speed);
        ellipse(x, y, speed, speed);
    }
    public void settings() {  fullScreen();  smooth(); }
}
