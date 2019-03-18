package app.conversion;

import app.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Grayscale {

    public BufferedImage transform(BufferedImage input) {
        BufferedImage output = Utils.deepCopy(input);
        Utils.get2DStream(input.getWidth(), input.getHeight()).forEach(
                p -> {
                    int px = input.getRGB(p.x, p.y);
                    int a = (px>>24)&0xff;
                    int r = (px>>16)&0xff;
                    int g = (px>>8)&0xff;
                    int b = px&0xff;

                    int avg = (r+g+b)/3;

                    // replace RGB value with avg
                    px = (a<<24) | (avg<<16) | (avg<<8) | avg;

                    output.setRGB(p.x, p.y, px);
                }
        );
        return output;
    }
}
