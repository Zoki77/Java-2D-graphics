/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rg.java.klase;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author zoran
 */
public class Persp {

    private Graphics g;
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    private int xsize;
    private int ysize;
    private double x;
    private double y;
    private double z;
    private double i;
    private double j;
    private double k;
    private double d;
    private double mtr[][] = new double[4][4];
    private double v[] = new double[4];
    private double vtr[] = new double[4];
    private double vtrx[] = new double[4];
    private double[][] mtrKSK = new double[4][4];
    private double matrix1[][], matrix2[][];

    //Konstruktor
    public Persp(Graphics g, double xmin, double xmax, double ymin, double ymax, double d, int xsize, int ysize) {
        this.g = g;
        this.d = d;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.xsize = xsize;
        this.ysize = ysize;
    }

    //Metode
    public void postaviNa(double x, double y, double z) {
        v[0] = x;
        v[1] = y;
        v[2] = z;
        v[3] = 1;
        vtr[0] = 0.0;
        vtr[1] = 0.0;
        vtr[2] = 0.0;
        vtr[3] = 0.0;
        vtrx[0] = 0.0;
        vtrx[1] = 0.0;
        vtrx[2] = 0.0;
        vtrx[3] = 0.0;

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                vtr[a] = vtr[a] + mtr[a][b] * v[b];
            }
        }


        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                vtrx[a] = vtrx[a] + mtrKSK[a][b] * vtr[b];
            }
        }
        this.x = -(d / vtrx[2]) * vtrx[0];
        this.y = -(d / vtrx[2]) * vtrx[1];
        this.z = vtrx[2];

    }

    public void linijaDo(double x, double y, double z) {
        v[0] = x;
        v[1] = y;
        v[2] = z;
        v[3] = 1;
        vtr[0] = 0.0;
        vtr[1] = 0.0;
        vtr[2] = 0.0;
        vtr[3] = 0.0;
        vtrx[0] = 0.0;
        vtrx[1] = 0.0;
        vtrx[2] = 0.0;
        vtrx[3] = 0.0;

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                vtr[a] = vtr[a] + mtr[a][b] * v[b];
            }
        }

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                vtrx[a] = vtrx[a] + mtrKSK[a][b] * vtr[b];
            }
        }
        this.i = -(d / vtrx[2]) * vtrx[0];
        this.j = -(d / vtrx[2]) * vtrx[1];
        this.k = vtrx[2];



        g.drawLine((int) (this.x * xsize / (xmax - xmin) - ((int) (xmin * xsize / (xmax - xmin)))),
                (int) (this.y * ysize / (ymax - ymin) + (ymax * ysize / (ymax - ymin))),
                (int) (this.i * xsize / (xmax - xmin) - ((int) (xmin * xsize / (xmax - xmin)))),
                (int) (this.j * ysize / (ymax - ymin) + (ymax * ysize / (ymax - ymin))));
        this.x = this.i;
        this.y = this.j;
        this.z = this.k;

    }

    public void KSK(double x0, double y0, double z0, double x1, double y1, double z1, double Vx, double Vy, double Vz) {
        this.mtrKSK = new double[4][4];

        double ux, uy, uz, vx, vy, vz, nx, ny, nz, N, U, V;
        nx = x0 - x1;
        ny = y0 - y1;
        nz = z0 - z1;
        N = Math.sqrt(Math.pow(nx, 2) + Math.pow(ny, 2) + Math.pow(nz, 2));
        nx = nx / N;
        ny = ny / N;
        nz = nz / N;

        ux = (Vy * nz) - (Vz * ny);
        uy = -((Vx * nz) - (Vz * nx));
        uz = (Vx * ny) - (Vy * nx);
        U = Math.sqrt(Math.pow(ux, 2) + Math.pow(uy, 2) + Math.pow(uz, 2));
        ux = ux / U;
        uy = uy / U;
        uz = uz / U;

        vx = (ny * uz) - (nz * uy);
        vy = -((nx * uz) - (nz * ux));
        vz = (nx * uy) - (ny * ux);
        V = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2) + Math.pow(vz, 2));
        vx = vx / V;
        vy = vy / V;
        vz = vz / V;

        matrix1 = new double[][]{{ux, uy, uz, 0}, {vx, vy, vz, 0}, {nx, ny, nz, 0}, {0, 0, 0, 1}};
        matrix2 = new double[][]{{1, 0, 0, -x0}, {0, 1, 0, -y0}, {0, 0, 1, -z0}, {0, 0, 0, 1}};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    mtrKSK[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
    }

    public void postaviBoju(Color c) {
        g.setColor(c);
    }

    public void trans(MT3D m) {
        this.mtr = m.getMt();
    }

    public void crtajKocku(int a, int b, int c, int i, int j, int k) {
        postaviNa(a, -b, c);
        linijaDo(i, -b, c);
        linijaDo(i, -j, c);
        linijaDo(a, -j, c);
        linijaDo(a, -b, c);
        linijaDo(a, -b, k);
        linijaDo(i, -b, k);
        linijaDo(i, -j, k);
        linijaDo(a, -j, k);
        linijaDo(a, -b, k);
        postaviNa(i, -b, c);
        linijaDo(i, -b, k);
        postaviNa(i, -j, c);
        linijaDo(i, -j, k);
        postaviNa(a, -j, c);
        linijaDo(a, -j, k);
    }

    public void stozac(double r, double h, int n) {
        double fi;

        for (fi = 0.0; fi <= 2 * Math.PI; fi = fi + (2 * Math.PI / n)) {
            postaviNa(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(0, -h, 0);
            postaviNa(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi + (2 * Math.PI / n)), 0, r * Math.sin(fi + (2 * Math.PI / n)));
        }
    }

    public void valjak(double r, int h, int n) {
        double fi;

        for (fi = 0.0; fi <= 2 * Math.PI; fi = fi + (2 * Math.PI / n)) {
            postaviNa(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi), -h, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi + (2 * Math.PI / n)), -h, r * Math.sin(fi + (2 * Math.PI / n)));
            postaviNa(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi + (2 * Math.PI / n)), 0, r * Math.sin(fi + (2 * Math.PI / n)));
        }

    }

    public void kugla(double r, int m, int n,int lvl) {
        double fi, delta, xa, yb, zc;
        postaviNa(0, r, 0);
        for (fi = 0.0; fi <= 2 * Math.PI/lvl; fi = fi + (Math.PI / n)) {
            for (delta = 0.0; delta <= 2 * Math.PI/lvl; delta = delta + (2 * Math.PI / 200)) {
                xa = r * Math.sin(fi) * Math.cos(delta);
                yb = r * Math.sin(fi) * Math.sin(delta);
                zc = r * Math.cos(fi);
                linijaDo(xa, zc, yb);
            }
            xa = r * Math.sin(fi + Math.PI / n) * Math.cos(0);
            yb = r * Math.sin(fi + Math.PI / n) * Math.sin(0);
            zc = r * Math.cos(fi + Math.PI / n);
            postaviNa(xa, zc, yb);

        }

        postaviNa(0, r, 0);
        for (delta = 0.0; delta <= 2 * Math.PI/lvl; delta = delta + (2 * Math.PI / m)) {
            postaviNa(0, r, 0);
            for (fi = 0.0; fi <= 2 * Math.PI/lvl; fi = fi + (2 * Math.PI / 200)) {
                xa = r * Math.sin(fi) * Math.cos(delta);
                yb = r * Math.sin(fi) * Math.sin(delta);
                zc = r * Math.cos(fi);
                linijaDo(xa, zc, yb);
            }
        }
    }
    
        public void kotac(double r, double h, int n) {
        double fi;

        for (fi = 0.0; fi <= 2 * Math.PI; fi = fi + (2 * Math.PI / n)) {
            postaviNa(0,0,0);
            linijaDo(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi), -h, r * Math.sin(fi));
            linijaDo(0, -h, 0);
            postaviNa(r * Math.cos(fi), -h, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi + (2 * Math.PI / n)), -h, r * Math.sin(fi + (2 * Math.PI / n)));
            postaviNa(r * Math.cos(fi), 0, r * Math.sin(fi));
            linijaDo(r * Math.cos(fi + (2 * Math.PI / n)), 0, r * Math.sin(fi + (2 * Math.PI / n)));
        }

    }

    // Getteri i setteri
    public Graphics getG() {
        return g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }

    public double getXmin() {
        return xmin;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getXmax() {
        return xmax;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public double getYmin() {
        return ymin;
    }

    public void setYmin(double ymin) {
        this.ymin = ymin;
    }

    public double getYmax() {
        return ymax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
    }

    public int getXsize() {
        return xsize;
    }

    public void setXsize(int xsize) {
        this.xsize = xsize;
    }

    public int getYsize() {
        return ysize;
    }

    public void setYsize(int ysize) {
        this.ysize = ysize;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getJ() {
        return j;
    }

    public void setJ(double j) {
        this.j = j;
    }
}
