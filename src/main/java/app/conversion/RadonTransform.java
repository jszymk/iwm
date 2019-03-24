package app.conversion;

import app.bresenham.Bresenham;
import app.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.Math.PI;

public class RadonTransform {

    private double dalpha = 0;
    private int n = 0;
    private double l = 0;

    public RadonTransform(int n, double l, double dalpha) {
        this.dalpha = dalpha*PI/180.0;
        this.n = n;
        this.l = l*PI/180.0;
    }

    public BufferedImage transform(BufferedImage input) throws IOException {

        double r = input.getHeight()*Math.sqrt(2)/2;
        double h = 2*r*Math.sin(l/2)/n;
        double dB = l/n;

        int sW = (int)(PI/dalpha);
        int sH = n;

        double[][] transform = new double[sW][sH];
        for(int x = 0; x < sW; ++x) {
            double alpha = x*dalpha;
            double slope = Math.tan(alpha);
            if(slope == 0.0) slope = 1e-10;
            for(int y = 0; y < sH; ++y) {
                double beta = (sH/2-y);
                double intercept = r*Math.sin(beta*dB)/Math.cos(alpha) + input.getHeight()/2 - slope*input.getHeight()/2;
                double avg = Bresenham.gen(slope, intercept, input.getHeight())
                        .filter(p -> p.x > 0 && p.x < input.getWidth() && p.y > 0 && p.y < input.getHeight())
                        .mapToInt(p -> {
                    int rgb = input.getRGB(p.x, p.y);
                    int R = (rgb>>16)&0xff;
                    int G = (rgb>>8)&0xff;
                    int B = rgb&0xff;
                    return (R+G+B)/3;
                }).average().orElse(0);
                transform[x][y] = avg;
            }
        }

        double maxValue = Arrays.stream(transform).flatMapToDouble(Arrays::stream).max().orElse(0);
        int[][] normalized = Arrays.stream(transform).map(arr -> Arrays.stream(arr).mapToInt(val -> (int)(val*255/maxValue)).toArray()).toArray(int[][]::new);

        BufferedImage output = new BufferedImage(sW, sH, BufferedImage.TYPE_INT_RGB);
        Utils.get2DStream(sW, sH).forEach(p -> {
            int norm = normalized[p.x][p.y];
            int px = norm | norm<<8 | norm<<16;
            output.setRGB(p.x, p.y, px);
        });
        return output;
    }
}
