package app.bresenham;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Bresenham {

    public static Stream<Point> gen(double a, double b, int size) {
        if(a > 1 || a < -1) {
            return bresenhamNaive(1/a, -b/a, size).map(p -> new Point(p.y, p.x));
        }
        else return bresenhamNaive(a, b, size);
    }

    private static Stream<Point> bresenhamNaive(double a, double b, int xEnd)
    {
        List<Point> points = new ArrayList<>();
        double slopeError = 0;

        for (int x = 0, y = (int)b; x <= xEnd; x++) {
            points.add(new Point(x, y));
            slopeError += Math.abs(a);

            if (slopeError >= 0) {
                y += Math.signum(a);
                slopeError -= 1;
            }
        }
        return points.stream();
    }

}
