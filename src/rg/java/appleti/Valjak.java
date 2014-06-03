/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rg.java.appleti;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Thread.sleep;
import rg.java.klase.MT3D;
import rg.java.klase.Persp;

/**
 *
 * @author zoran
 */
public class Valjak extends Applet {

    private Persp ortho;
    private MT3D mt1;
    //private int iy = 0;
    private double kut, y;
    private int vis=8;

    class Animacija extends Thread {

        long pauza, kraj;

        Animacija(double fps, double trajanje) {
            // fps - broj sličica u sekundi
            // trajanje - trajanje u sekundama

            // pauza između sličica u milisekundama
            pauza = Math.round(1000.0 / fps);

            // poslije koliko koraka animacija završava
            kraj = Math.round(trajanje * fps);
        } // Animacija

        public void run() {
            long brojac = 0;

            while (brojac++ < kraj) {
                try {
                    sleep(pauza); // pauza u milisekundama
                } catch (InterruptedException e) {
                }
                kut += Math.PI / 25;
                if (kut >= 2 * Math.PI) {
                    y -= 3;
                    kut = 0;
                    vis +=2;
                }
                repaint(); // traži ponovno iscrtavanje
            }
        } // run
    } // class Animacija

    public void init() {
        setBackground(Color.yellow);
        // 30 sličica u sekundi u trajanju 20 sekundi
        (new Valjak.Animacija(30.0, 25.0)).start();
    } // init

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {

        int w = getWidth();
        int h = getHeight();

        Image slika = createImage(w, h);
        Graphics gs = slika.getGraphics();
        ortho = new Persp(gs, -10, 10, -10, 10, 21, 500, 500);

        ortho.KSK(20 * Math.cos(kut), y, 20 * Math.sin(kut), 0, 0, 0, 0, 1, 0);


        ortho.postaviBoju(Color.yellow);
        setBackground(gs.getColor());
        resize(ortho.getXsize(), ortho.getYsize());


        mt1 = new MT3D();
        mt1.identitet();


        // crtanje prvaca
        ortho.trans(mt1);
        ortho.postaviBoju(Color.red);
        for (int i = -5; i <= 5; i++) {

            ortho.postaviNa(i, 0, 5);
            ortho.linijaDo(i, 0, -5);

            ortho.postaviNa(5, 0, i);
            ortho.linijaDo(-5, 0, i);
        };


        ortho.postaviBoju(Color.black);
        //ortho.trans(mt1);
        ortho.valjak(4, 8, vis);
        g.drawImage(slika, 0, 0, null);

    }



}
