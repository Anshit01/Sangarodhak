package com.hashbash.sangarodhak.MiniGames;

import processing.core.PApplet;
import processing.core.PFont;

public class TicTacToe extends PApplet {

//Tres en raya, 28/03/19, Gonzalo Ortega.

    PFont fuente;

    private int forma,partida,a1,a2,a3,b1,b2,b3,c1,c2,c3;

    //Se centra el cuadrado y se ajusta su tamaño al de la ventana.
    private int cX, cY, l, tam, x, y;

    public void setup() {

        //size(600, 600);
        background(50);
        forma = 1;
        partida = 1;
        vaciarCasillas();
    }

    public void draw() {
        //Ajustar el lado del tablero.
        //l = height/5*2;
        //if (width>=height) {
        //  l = width/5*2;
        //}
        l = Math.min(width, height) - 30;
        cX = width / 2;
        cY = height / 2;
        cuadricula(cX, cY, l);

        textSize(l / 7);
        fill(255);
        textAlign(CENTER, CENTER); //Centrar el texto.
        fill(220);
        noStroke();

        //Decidir el ganador.
        if (a1 + a2 + a3 == 3 || b1 + b2 + b3 == 3 || c1 + c2 + c3 == 3 ||
                a1 + b1 + c1 == 3 || a2 + b2 + c2 == 3 || a3 + b3 + c3 == 3 ||
                a1 + b2 + c3 == 3 || a3 + b2 + c1 == 3) {
            fill(100);
            rect(0, cY - l / 6, width, l / 3);
            fill(220);
            text("Circle Wins", cX, cY - l / 25); //(-l/25) para acabar de centrar el texto.
            partida = 2;
        }
        if (a1 + a2 + a3 == -3 || b1 + b2 + b3 == -3 || c1 + c2 + c3 == -3 ||
                a1 + b1 + c1 == -3 || a2 + b2 + c2 == -3 || a3 + b3 + c3 == -3 ||
                a1 + b2 + c3 == -3 || a3 + b2 + c1 == -3) {
            fill(100);
            rect(0, cY - l / 6, width, l / 3);
            fill(220);
            text("Cross Wins", cX, cY - l / 25);
            partida = 2;
        } else if (a1 != 0 && a2 != 0 && a3 != 0 &&
                b1 != 0 && b2 != 0 && b3 != 0 &&
                c1 != 0 && c2 != 0 && c3 != 0) {
            fill(100);
            rect(0, cY - l / 6, width, l / 3);
            fill(220);
            text("It's a Draw", cX, cY - l / 25);
            partida = 2;
        }
    }

    public void mousePressed() {
        tam = l * 2 / 15; //Definir tamaño de las figuras.
        strokeWeight(l / 60);
        stroke(220);
        fill(0, 0, 0, 0);
        x = mouseX;
        y = mouseY;
        colocarFicha(x, y);

        //Volver a empezar.
        if (partida == 2) {
            background(50);
            vaciarCasillas();
            cuadricula(cX, cY, l);
            colocarFicha(x, y);
            partida = 1;
        }
    }

    //Dibujar las fichas.
    public void ficha(int x, int y, int forma) {
        if (forma == 1) {
            //Dibujar O y cambiar de forma.
            ellipse(x, y, tam * 2, tam * 2);
        } else {
            //Dibujar X del mismo tamaño que O.
            line(x - tam * cos(PI / 4), y - tam * sin(PI / 4),
                    x + tam * cos(PI / 4), y + tam * sin(PI / 4));
            line(x - tam * cos(PI / 4), y + tam * sin(PI / 4),
                    x + tam * cos(PI / 4), y - tam * sin(PI / 4));
        }
    }

    //Dibujar la cuadrícula.
    public void cuadricula(int cX, int cY, int l) {
        strokeWeight(l / 60);
        stroke(220);
        fill(0, 0, 0, 0); //Fondo transparente.

        //Dibujar fila 1.
        rect(cX - l / 2, cY - l / 2, l / 3, l / 3);
        rect(cX - l / 6, cY - l / 2, l / 3, l / 3);
        rect(cX + l / 6, cY - l / 2, l / 3, l / 3);

        //Dibujar fila 2.
        rect(cX - l / 2, cY - l / 6, l / 3, l / 3);
        rect(cX - l / 6, cY - l / 6, l / 3, l / 3);
        rect(cX + l / 6, cY - l / 6, l / 3, l / 3);

        //Dibujar fila 3.
        rect(cX - l / 2, cY + l / 6, l / 3, l / 3);
        rect(cX - l / 6, cY + l / 6, l / 3, l / 3);
        rect(cX + l / 6, cY + l / 6, l / 3, l / 3);
    }

    //Colocar las fichas y asignar valores a las casilla.
    public void colocarFicha(int x, int y) {
        //Clic en fila 1.
        if ((x > cX - l / 2) && (x < cX - l / 6) && (y > cY - l / 2) && (y < cY - l / 6)) {
            if (a1 == 0) {
                ficha(cX - l / 3, cY - l / 3, forma);
                a1 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX - l / 6) && (x < cX + l / 6) && (y > cY - l / 2) && (y < cY - l / 6)) {
            if (a2 == 0) {
                ficha(cX, cY - l / 3, forma);
                a2 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX + l / 6) && (x < cX + l / 2) && (y > cY - l / 2) && (y < cY - l / 6)) {
            if (a3 == 0) {
                ficha(cX + l / 3, cY - l / 3, forma);
                a3 = forma;
                forma = forma * (-1);
            }
        }

        //Clic en fila 2.
        if ((x > cX - l / 2) && (x < cX - l / 6) && (y > cY - l / 6) && (y < cY + l / 6)) {
            if (b1 == 0) {
                ficha(cX - l / 3, cY, forma);
                b1 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX - l / 6) && (x < cX + l / 6) && (y > cY - l / 6) && (y < cY + l / 6)) {
            if (b2 == 0) {
                ficha(cX, cY, forma);
                b2 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX + l / 6) && (x < cX + l / 2) && (y > cY - l / 6) && (y < cY + l / 6)) {
            if (b3 == 0) {
                ficha(cX + l / 3, cY, forma);
                b3 = forma;
                forma = forma * (-1);
            }
        }

        //Clic en fila 3.
        if ((x > cX - l / 2) && (x < cX - l / 6) && (y > cY + l / 6) && (y < cY + l / 2)) {
            if (c1 == 0) {
                ficha(cX - l / 3, cY + l / 3, forma);
                c1 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX - l / 6) && (x < cX + l / 6) && (y > cY + l / 6) && (y < cY + l / 2)) {
            if (c2 == 0) {
                ficha(cX, cY + l / 3, forma);
                c2 = forma;
                forma = forma * (-1);
            }
        }
        if ((x > cX + l / 6) && (x < cX + l / 2) && (y > cY + l / 6) && (y < cY + l / 2)) {
            if (c3 == 0) {
                ficha(cX + l / 3, cY + l / 3, forma);
                c3 = forma;
                forma = forma * (-1);
            }
        }
    }

    public void vaciarCasillas() {
        a1 = 0;
        a2 = 0;
        a3 = 0;
        b1 = 0;
        b2 = 0;
        b3 = 0;
        c1 = 0;
        c2 = 0;
        c3 = 0;
    }

    public void settings() {
        fullScreen();
    }
}
