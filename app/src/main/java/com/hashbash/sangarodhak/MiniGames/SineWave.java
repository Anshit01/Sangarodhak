package com.hashbash.sangarodhak.MiniGames;

import processing.core.PApplet;

public class SineWave extends PApplet {

    /*
     * [CalsignLabs]
     * This example taken from the Processing distribution.
     * This example has been scaled up to fill the entire display.
     */

    /**
     * Sine Wave
     * by Daniel Shiffman.
     *
     * Render a simple sine wave.
     */

    int xspacing = 8;   // How far apart should each horizontal location be spaced
    int w;              // Width of entire wave

    float theta = 0.0f;  // Start angle at 0
    float amplitude = 75.0f;  // Height of wave
    float period = 500.0f;  // How many pixels before the wave repeats
    float dx;  // Value for incrementing X, a function of period and xspacing
    float[] yvalues;  // Using an array to store height values for the wave

    public void setup() {

        frameRate(30);
        colorMode(RGB,255,255,255,100);

        w = width+16;
        dx = (TWO_PI / period) * xspacing;
        yvalues = new float[w/xspacing];
    }

    public void draw() {
        background(0);
        calcWave();
        renderWave();

    }

    public void calcWave() {
        // Increment theta (try different values for 'angular velocity' here
        theta += 0.02f;

        // For every x value, calculate a y value with sine function
        float x = theta;
        for (int i = 0; i < yvalues.length; i++) {
            yvalues[i] = sin(x)*amplitude;
            x+=dx;
        }
    }

    public void renderWave() {
        // A simple way to draw the wave with an ellipse at each location
        translate(0, -height/2 + amplitude/2);
        for (int x = 0; x < yvalues.length; x++) {
            noStroke();
            fill(255,50);

            ellipseMode(CENTER);
            ellipse(x*xspacing,width/2+yvalues[x],16,16);
        }
    }
    public void settings() {  fullScreen();  smooth(); }
}
