package app.filter;

import app.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageFilter {

    public static BufferedImage apply(BufferedImage image, double[][] mask) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        double[][] conv = new double[image.getWidth()][image.getHeight()];
        for(int x = 1; x < image.getWidth()-1; ++x) {
            for(int y = 1; y < image.getHeight()-1; ++y) {
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        int RGB = image.getRGB(x+i-1, y+j-1);
                        int r = ((RGB>>16)&0xff);
                        int g = ((RGB>>8)&0xff);
                        int b = (RGB&0xff);
                        double avg = (r+g+b)/3;
                        conv[x][y] += avg*mask[i][j];
                    }
                }
            }
        }
        double maxValue = Arrays.stream(conv).flatMapToDouble(Arrays::stream).max().orElse(0);
        int[][] normalized = Arrays.stream(conv).map(arr -> Arrays.stream(arr).mapToInt(val -> (int)(val*255/maxValue)).toArray()).toArray(int[][]::new);
        Utils.get2DStream(image.getWidth(), image.getHeight()).forEach(p -> {
            int norm = normalized[p.x][p.y];
            int px = norm | norm<<8 | norm<<16;
            output.setRGB(p.x, p.y, px);
        });
        return output;
    }
}
