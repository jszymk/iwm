package app.filter;

import app.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageFilter {

    public static BufferedImage apply(BufferedImage image, double[] mask) {
        double[][] conv = new double[image.getWidth()][image.getHeight()];
        for(int x = 2; x < image.getWidth()-2; ++x) {
            for(int y = 2; y < image.getHeight()-2; ++y) {
                for(int i = 0; i < 5; ++i) {
                    int RGB = image.getRGB(x+i-2, y);
                    int r = (RGB>>16)&0xff;
                    int g = (RGB>>8)&0xff;
                    int b = RGB&0xff;
                    double avg = (r+g+b)/3;
                    conv[x][y] += avg*mask[i];
                }
            }
        }
        double maxValue = Arrays.stream(conv).flatMapToDouble(Arrays::stream).max().orElse(0);
        double minValue = Arrays.stream(conv).flatMapToDouble(Arrays::stream).min().orElse(0);
        int[][] normalized = Arrays.stream(conv).map(arr -> Arrays.stream(arr).mapToInt(val -> (int)(((Math.max(val,0))/(maxValue))*255)).toArray()).toArray(int[][]::new);

        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Utils.get2DStream(image.getWidth(), image.getHeight()).forEach(p -> {
            int norm = normalized[p.x][p.y];
            int px = norm | norm<<8 | norm<<16;
            output.setRGB(p.x, p.y, px);
        });
        return output;
    }
}
