package byog.lab5;

import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    /*
     * Draws a hexagon of side len s at lower left corner (x, y)
     */
    public static void addHexagon(TETile[][] world, int s, int x, int y, TETile t) {
        int width = s * 3 - 2, height = 2 * s;
        for (int i = 0; i < height; i++) {
            int l = (i < s) ? s - i - 1 : i - s, r = width - 1 - l;
            System.out.println("i = " + i + " l = " + l + " r = " + r);
            for (int j = l; j <= r; j++) {
                world[x + j][y + i] = t;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addHexagon(world, 5, 2, 3, Tileset.FLOOR);
        ter.renderFrame(world);
    }
}
