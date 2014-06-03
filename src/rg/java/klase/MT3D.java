/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rg.java.klase;

/**
 *
 * @author zoran
 */
public class MT3D {

    private double kut;
    private double mt[][];
    private double mt1[][];
    private double mt2[][];
//    private double b, c, d;
//    private double alfa, beta;

    public void pomakni(double px, double py, double pz) {
        identitet();
        this.mt[0][3] = px;
        this.mt[1][3] = -py;
        this.mt[2][3] = pz;

    }

    public void skaliraj(double sx, double sy, double sz) {
        identitet();
        this.mt[0][0] = sx;
        this.mt[1][1] = sy;
        this.mt[2][2] = sz;
    }

    public void rotirajX(double kut) {
        this.kut = Math.toRadians(kut);
        this.kut = -this.kut;
        identitet();
        this.mt[1][1] = Math.cos(this.kut);
        this.mt[1][2] = -Math.sin(this.kut);
        this.mt[2][1] = Math.sin(this.kut);
        this.mt[2][2] = Math.cos(this.kut);

    }

    public void rotirajY(double kut) {
        this.kut = Math.toRadians(kut);
        this.kut = -this.kut;
        identitet();
        this.mt[0][0] = Math.cos(this.kut);
        this.mt[2][0] = -Math.sin(this.kut);
        this.mt[0][2] = Math.sin(this.kut);
        this.mt[2][2] = Math.cos(this.kut);

    }

    public void rotirajZ(double kut) {
        this.kut = Math.toRadians(kut);
        this.kut = -this.kut;
        identitet();
        this.mt[0][0] = Math.cos(this.kut);
        this.mt[0][1] = -Math.sin(this.kut);
        this.mt[1][0] = Math.sin(this.kut);
        this.mt[1][1] = Math.cos(this.kut);

    }

    public void identitet() {
        this.mt = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    this.mt[i][j] = 1;
                } else {
                    this.mt[i][j] = 0;
                }
            }
        }
    }

    public void mult(MT3D m) {
        mt1 = new double[4][4];
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                mt1[a][b] = this.mt[a][b];
            }
        }
        mt2 = m.getMt();
        mt = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    this.mt[i][j] = this.mt[i][j] + this.mt1[i][k] * mt2[k][j];
                }
            }
        }
    }

//    public void izracunajAlfaBeta(double x1, double y1, double z1, double x2, double y2, double z2) {
//        b = (y2 - y1) / (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
//        c = (z2 - z1) / (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
//        d = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
//        alfa = Math.toDegrees(Math.asin(b / d));
//        beta = Math.toDegrees(Math.acos(d));
//    }
//
//    public void rotiraj(double x1, double y1, double z1, double x2, double y2, double z2, double kut) {
//        izracunajAlfaBeta(x1, y1, z1, x2, y2, z2);
//        pomakni(-x1, -y1, -z1);
//        rotirajX(alfa);
//        rotirajY(-beta);
//        rotirajZ(kut);
//        rotirajY(beta);
//        rotirajX(-alfa);
//        pomakni(x1, y1, z1);
//    }
    //Getteri i setteri

    public double[][] getMt() {
        return mt;
    }

    public void setMt(double[][] mt) {
        this.mt = mt;
    }
}
