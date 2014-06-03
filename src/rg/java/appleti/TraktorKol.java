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
public class TraktorKol extends Applet {

    private Persp ortho;
    private double kut, y;
    private int iy = 0;
    private MT3D mt1;
    private MT3D mt2;
    private MT3D mt3;
    private MT3D mt4;
    private double p = 0;

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
            int korak = 3;


            while (brojac++ < kraj) {
                try {
                    sleep(pauza); // pauza u milisekundama
                } catch (InterruptedException e) {
                }
                kut += Math.PI / 150;
                if (kut >= 2 * Math.PI) {
                    y -= 5;
                    kut = 0;
                }
                p -= 0.1;
                iy += korak;
                repaint(); // traži ponovno iscrtavanje
            }
        } // run
    } // class Animacija

    public void init() {
        setBackground(Color.yellow);
        // 30 sličica u sekundi u trajanju 20 sekundi
        (new TraktorKol.Animacija(30.0, 10.0)).start();
    } // init

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {

        int w = getWidth();
        int h = getHeight();

        mt1 = new MT3D();
        mt1.identitet();
        mt2 = new MT3D();
        mt2.identitet();
        mt3 = new MT3D();
        mt3.identitet();
        mt4 = new MT3D();
        mt4.identitet();

        Image slika = createImage(w, h);
        Graphics gs = slika.getGraphics();
        ortho = new Persp(gs, -10, 10, -10, 10, 17, 500, 500);

        //zračna perspektiva
        ortho.KSK(10, -15, 10, 0, 0, 0, 0, 1, 0);
        // perspektiva s zemlje
        //ortho.KSK(10, 0, 10, 0, 0, 0, 0, 1, 0);


        ortho.postaviBoju(Color.yellow);
        setBackground(gs.getColor());
        resize(ortho.getXsize(), ortho.getYsize());

        //crtanje mreže
        ortho.postaviBoju(Color.green);
        ortho.trans(mt1);
        for (int i = -20; i <= 20; i++) {

            ortho.postaviNa(i, 0, 20);
            ortho.linijaDo(i, 0, -20);

            ortho.postaviNa(20, 0, i);
            ortho.linijaDo(-20, 0, i);
        }

        //crtanje vjetrenjače
        ortho.postaviBoju(Color.black);

        mt1.pomakni(0 + p, 1.2, 0);
        ortho.trans(mt1);
        ortho.crtajKocku(0, 0, 0, 5, 3, 2);
        mt1.pomakni(0 + p, 2.4, 0);
        ortho.trans(mt1);
        ortho.crtajKocku(5, 0, -1, 8, 5, 3);
        //osovina prenja
        mt1.rotirajX(90);
        mt2.pomakni(1 + p, 1.2, -1);
        mt2.mult(mt1);
        ortho.trans(mt2);
        ortho.valjak(0.2, 4, 10);
        //osovina zadnja
        mt2.rotirajY(-iy);
        mt1.rotirajX(90);
        mt2.pomakni(7 + p, 2.4, -1);
        mt4.mult(mt2);
        mt2.mult(mt1);
        ortho.trans(mt2);
        ortho.valjak(0.2, 4, 10);
        //kotac prednji lijevi
        mt2.rotirajY(-iy);
        mt4.rotirajX(90);
        mt3.pomakni(1 + p, 1.2, 3);
        mt4.mult(mt2);
        mt3.mult(mt4);
        ortho.trans(mt3);
        ortho.kotac(1.2, 0.4, 20);
        //kotac prednji desni
        mt2.rotirajY(-iy);
        mt4.rotirajX(90);
        mt3.pomakni(1 + p, 1.2, -1.4);
        mt4.mult(mt2);
        mt3.mult(mt4);
        ortho.trans(mt3);
        ortho.kotac(1.2, 0.4, 20);
        //kotac zadnji lijevi
        mt2.rotirajY(-iy);
        mt4.rotirajX(90);
        mt3.pomakni(7 + p, 2.4, 3);
        mt4.mult(mt2);
        mt3.mult(mt4);
        ortho.trans(mt3);
        ortho.kotac(2.4, 0.4, 20);
        //kotac zadnji desni
        mt2.rotirajY(-iy);
        mt4.rotirajX(90);
        mt3.pomakni(7 + p, 2.4, -1.4);
        mt4.mult(mt2);
        mt3.mult(mt4);
        ortho.trans(mt3);
        ortho.kotac(2.4, 0.4, 20);
        g.drawImage(slika, 0, 0, null);


    }
}
