/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.utils;

import java.awt.image.BufferedImage;

/**
 *
 * @author Weronika
 */
public class MSE {

    private double sum = 0.0;
    private double mse;

    private double redSum = 0.0;
    private double greenSum = 0.0;
    private double blueSum = 0.0;

    public double calculateMSE(BufferedImage in, BufferedImage out) {
        
        //System.out.println(in.getHeight() + " " + in.getWidth());
        //System.out.println(out.getHeight() + " " + out.getWidth());

        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {

                int clrIn = in.getRGB(x, y);
                int clrOut = out.getRGB(x, y);

                int redIn = (clrIn & 0x00ff0000) >> 16;
                int redOut = (clrOut & 0x00ff0000) >> 16;

                double errRed = redOut - redIn;
                this.redSum += errRed * errRed;

                int greenIn = (clrIn & 0x0000ff00) >> 8;
                int greenOut = (clrOut & 0x0000ff00) >> 8;

                double errGreen = greenOut - greenIn;
                this.greenSum += errGreen * errGreen;

                int blueIn = clrIn & 0x000000ff;
                int blueOut = clrOut & 0x000000ff;

                double errBlue = blueOut - blueIn;
                this.blueSum += errBlue * errBlue;

                double err = out.getRGB(x, y) - in.getRGB(x, y);
                sum+= err*err;
            }
        }

        this.sum = (this.redSum + this.greenSum + this.blueSum) / 3;
        this.mse = (double) this.sum / (in.getHeight() * in.getWidth());
        
        //System.out.println(this.mse);
        return this.mse;
    }

}
