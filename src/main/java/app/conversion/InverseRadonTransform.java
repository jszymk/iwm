package app.conversion;

import app.bresenham.Bresenham;
import app.image.Pixel;
import app.utils.Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.PI;

public class InverseRadonTransform {

    private double dalpha = 0;
    private int n = 0;
    private double l = 0;
    private BufferedImage original;

    public InverseRadonTransform(BufferedImage original, int n, double l, double dalpha) {
        this.original = original;
        this.dalpha = dalpha*PI/180.0;
        this.n = n;
        this.l = l*PI/180.0;
    }

    public List<BufferedImage> transform(BufferedImage input, int iter) throws IOException {

        //System.out.println("IRT " + input.getWidth() + " " + input.getHeight());
        //System.out.println("APP" + this.original.getWidth() + " " + this.original.getHeight());
        int size = this.original.getWidth();

        double r = input.getHeight()*Math.sqrt(2)/2;
        double dB = l/n;
        double h = 2*r*Math.sin(l/2)/n;

        int sW = input.getWidth();
        int sH = input.getHeight();

        Pixel[][] transform = new Pixel[size][size];
        for(int i = 0; i<size; ++i) {
            for(int j = 0; j<size; ++j) {
                transform[i][j] = new Pixel();
            }
        }

        List<BufferedImage> output = new ArrayList<>();

        for(int i = 0; i<iter; ++i) {
            for(int x = 0; x < sW; ++x) {
                BufferedImage curIter = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                double alpha = x*dalpha;
                double slope = Math.tan(alpha);
                if(slope == 0.0) slope = 1e-10;
                for(int y = 0; y < sH; ++y) {
                    double beta = (sH/2-y);
                    double intercept = r*Math.sin(beta*dB)/Math.cos(alpha) + size/2 - slope*size/2;

                    int rgb = input.getRGB(x, y);
                    int R = (rgb>>16)&0xff;
                    int G = (rgb>>8)&0xff;
                    int B = rgb&0xff;
                    int avg = (R+G+B)/3;

                    Bresenham.gen(slope, intercept, size)
                            .filter(p -> p.x > 0 && p.x < size && p.y > 0 && p.y < size)
                            .forEach(p -> {
                                transform[p.x][p.y].n += 1;
                                transform[p.x][p.y].val += avg;
                            });
                }
                double maxValue = Arrays.stream(transform).flatMapToDouble(arr -> Arrays.stream(arr).filter(p -> p.n > 0).mapToDouble(p -> p.val)).max().orElse(0);
                int[][] normalized = Arrays.stream(transform).map(arr -> Arrays.stream(arr).mapToInt(val -> (int)(val.val*255/maxValue)).toArray()).toArray(int[][]::new);
                Utils.get2DStream(size, size).forEach(p -> {
                    int norm = normalized[p.x][p.y];
                    int px = norm | norm<<8 | norm<<16;
                    curIter.setRGB(p.x, p.y, px);
                });
                output.add(curIter);
            }
        }
        return output;
    }
}
