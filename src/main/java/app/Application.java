package app;

import app.conversion.InverseRadonTransform;
import app.conversion.RadonTransform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Application {

    public static void main(String argv[]) throws Exception {
        File input_file = new File("inp.jpg");

        BufferedImage image = ImageIO.read(input_file);

        BufferedImage sin = new RadonTransform(400, 90, 0.5).transform(image);

        File f = new File("sin.jpg");
        ImageIO.write(sin, "jpg", f);

        BufferedImage out = new InverseRadonTransform(400, 90, 0.5).transform(sin, 5);

        f = new File("out.jpg");
        ImageIO.write(out, "jpg", f);
    }
}
