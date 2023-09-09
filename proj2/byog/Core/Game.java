package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
// import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    //    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int windowWidth = 40;
    public static final int windowHeight = 40;
    public int xPos;
    public int yPos;
    public TETile[][] Map;
    private Random RANDOM;
    private long seed;

    public TETile preObj;

    public Game() {
        //        StdDraw.setCanvasSize(windowWidth * 16, windowHeight * 16);
        //        Font font = new Font("Monaco", Font.BOLD, 20);
        //        StdDraw.setFont(font);
        //        StdDraw.setXscale(0, windowWidth);
        //        StdDraw.setYscale(0, windowHeight);
        //        StdDraw.clear(Color.BLACK);
        //        StdDraw.enableDoubleBuffering();
        Map = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Map[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Method used for playing a fresh game. The game should start from the main menu. */
    public void playWithKeyboard() {
        //        drawMenu();
        //        Game game = this;
        //        while (true) {
        //            while (!StdDraw.hasNextKeyTyped()) {}
        //            char c = Character.toUpperCase(StdDraw.nextKeyTyped());
        //            if (c != 'N' && c != 'L' && c != 'Q') continue;
        //            if (c == 'N') {
        //                getSeed();
        //                randomWorldGenerate();
        //            } else if (c == 'L') {
        //                game = LoadWorld();
        //                System.out.println("after load: preObj = " + game.preObj);
        //            } else {
        //                return;
        //            }
        //            break;
        //        }
        //        game.gameLogic();
    }

    public void gameLogic() {
        //        ter.initialize(WIDTH, HEIGHT);
        //        ter.renderFrame(Map);
        //
        //        while (true) {
        //            while (!StdDraw.hasNextKeyTyped()) {}
        //            char c = Character.toUpperCase(StdDraw.nextKeyTyped());
        //            if (c == ':') {
        //                while (!StdDraw.hasNextKeyTyped()) {}
        //                c = Character.toUpperCase(StdDraw.nextKeyTyped());
        //                if (c == 'Q') {
        //                    SaveWorld();
        //                    return;
        //                }
        //            } else {
        //                move(Map, c);
        //                ter.renderFrame(Map);
        //            }
        //        }
    }

    private void SaveWorld() {
        File f = new File("./byog/Core/game.txt");
        if (f.exists()) {
            try {
                FileOutputStream fs = new FileOutputStream(f);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(this);
                os.close();
                fs.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }
        }
    }

    private Game LoadWorld() {
        File f = new File("./byog/Core/game.txt");
        Game oldGame = null;
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                oldGame = (Game) os.readObject();
                os.close();
                fs.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return oldGame;
    }

    private void getSeed() {
        //        seed = 0;
        //        while (true) {
        //            while (!StdDraw.hasNextKeyTyped()) {}
        //            char c = StdDraw.nextKeyTyped();
        //            if (Character.isDigit(c)) {
        //                seed = seed * 10 + c - '0';
        //            } else {
        //                RANDOM = new Random(seed);
        //                return;
        //            }
        //        }
    }

    private void move(TETile[][] worldFrame, char c) {
        if (c != 'A' && c != 'S' && c != 'W' && c != 'D') return;
        if (c == 'A' && xPos == 0
                || c == 'S' && yPos == 0
                || c == 'W' && yPos == HEIGHT - 1
                || c == 'D' && xPos == WIDTH - 1) return;
        int nx = xPos, ny = yPos;
        if (c == 'A') {
            nx--;
        } else if (c == 'S') {
            ny--;
        } else if (c == 'W') {
            ny++;
        } else {
            nx++;
        }
        if (worldFrame[nx][ny].equals(Tileset.WALL)) return;
        worldFrame[xPos][yPos] = preObj;
        if (preObj.equals(Tileset.LOCKED_DOOR) || preObj.equals(Tileset.UNLOCKED_DOOR)) {
            if (worldFrame[nx][ny].equals(Tileset.FLOOR)) { // indoor
                worldFrame[xPos][yPos] = Tileset.LOCKED_DOOR;
            } else { // outdoor
                worldFrame[xPos][yPos] = Tileset.UNLOCKED_DOOR;
            }
        }
        preObj = worldFrame[nx][ny];
        worldFrame[nx][ny] = Tileset.PLAYER;
        xPos = nx;
        yPos = ny;
    }

    private void drawMenu() {
        //        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        //        StdDraw.clear(Color.BLACK);
        //        StdDraw.setPenColor(Color.WHITE);
        //        StdDraw.text(windowWidth / 2, windowHeight / 4 * 3, "CS61B: THE GAME");
        //        StdDraw.setFont(new Font("Monaco", Font.BOLD, 16));
        //        StdDraw.text(windowWidth / 2, windowHeight / 3 + 2, "New Game (N)");
        //        StdDraw.text(windowWidth / 2, windowHeight / 3, "Load Game (L)");
        //        StdDraw.text(windowWidth / 2, windowHeight / 3 - 2, "Quit (Q)");
        //        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series of
     * characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should behave
     * exactly as if the user typed these characters into the game after playing playWithKeyboard.
     * If the string ends in ":q", the same world should be returned as if the string did not end
     * with q. For example "n123sss" and "n123sss:q" should return the same world. However, the
     * behavior is slightly different. After playing with "n123sss:q", the game should save, and
     * thus if we then called playWithInputString with the string "l", we'd expect to get the exact
     * same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        input = input.toUpperCase();
        int index = 0;
        Game game = this;
        if (input.charAt(0) == 'N') {
            index = input.indexOf('S');
            String seed = input.substring(1, index);
            RANDOM = new Random(Long.parseLong(seed));
            randomWorldGenerate();
        } else if (input.charAt(0) == 'L') {
            game = LoadWorld();
        } else if (input.charAt(0) == 'Q') {
            return game.Map;
        }

        game.stringGameLogic(input.substring(index + 1, input.length()));
        return game.Map;
    }

    private void stringGameLogic(String input) {
        //        ter.initialize(WIDTH, HEIGHT);
        //        ter.renderFrame(Map);
        //        StdDraw.pause(500);

        int index = 0;
        while (index < input.length()) {
            char c = input.charAt(index++);
            if (c == ':') {
                c = input.charAt(index++);
                if (c == 'Q') {
                    SaveWorld();
                }
            } else {
                move(Map, c);
                //                ter.renderFrame(Map);
                //                StdDraw.pause(500);
            }
        }
    }

    public void randomWorldGenerate() {

        Rect preRect = null, curRect;
        for (int t = 0; t < 20; t++) {
            int x = RANDOM.nextInt(WIDTH), y = RANDOM.nextInt(HEIGHT);
            curRect = drawRect(Map, x, y);
            if (curRect != null) {
                if (preRect != null) {
                    drawRoad(Map, preRect, curRect);
                }
                preRect = curRect;
            }
        }

        drawWall(Map);
        drawDoorAndPlayer(Map);
    }

    private class Rect {
        public int x, y;
        public int xlen, ylen;

        public Rect(int x, int y, int xlen, int ylen) {
            this.x = x;
            this.y = y;
            this.xlen = xlen;
            this.ylen = ylen;
        }
    }

    private Rect drawRect(TETile[][] worldFrame, int x, int y) {
        int xlen = WIDTH - x, ylen = HEIGHT - y;
        while (xlen * 3 > WIDTH) xlen /= 3;
        while (ylen * 3 > HEIGHT) ylen /= 3;
        while (true) {
            xlen = RANDOM.nextInt(xlen);
            ylen = RANDOM.nextInt(ylen);
            if (xlen == 0 || ylen == 0) return null;
            int usednum = 0;
            for (int i = 0; i < xlen; i++)
                for (int j = 0; j < ylen; j++)
                    if (worldFrame[x + i][y + j].equals(Tileset.FLOOR)) usednum++;
            if ((double) usednum / xlen / ylen < 0.3) {
                for (int i = 0; i < xlen; i++)
                    for (int j = 0; j < ylen; j++) worldFrame[x + i][y + j] = Tileset.FLOOR;
                return new Rect(x, y, xlen, ylen);
            }
        }
    }

    private void drawRoad(TETile[][] worldFrame, Rect A, Rect B) {
        int xA = A.x + RANDOM.nextInt(A.xlen), yA = A.y + RANDOM.nextInt(A.ylen);
        int xB = B.x + RANDOM.nextInt(B.xlen), yB = B.y + RANDOM.nextInt(B.ylen);
        int xinc = (xA < xB) ? 1 : -1, yinc = (yA < yB) ? 1 : -1;
        while (xA != xB) {
            worldFrame[xA][yA] = Tileset.FLOOR;
            xA += xinc;
        }
        while (yA != yB) {
            worldFrame[xA][yA] = Tileset.FLOOR;
            yA += yinc;
        }
    }

    private void drawWall(TETile[][] worldFrame) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if ((x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1)
                        && worldFrame[x][y].equals(Tileset.FLOOR)) worldFrame[x][y] = Tileset.WALL;
                else if (worldFrame[x][y].equals(Tileset.NOTHING)) {
                    if (x > 0 && worldFrame[x - 1][y].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (x < WIDTH - 1 && worldFrame[x + 1][y].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (y > 0 && worldFrame[x][y - 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (y < HEIGHT - 1 && worldFrame[x][y + 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (x > 0 && y > 0 && worldFrame[x - 1][y - 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (x > 0
                            && y < HEIGHT - 1
                            && worldFrame[x - 1][y + 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (x < WIDTH - 1
                            && y > 0
                            && worldFrame[x + 1][y - 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                    else if (x < WIDTH - 1
                            && y < HEIGHT - 1
                            && worldFrame[x + 1][y + 1].equals(Tileset.FLOOR))
                        worldFrame[x][y] = Tileset.WALL;
                }
            }
        }
    }

    private void drawDoorAndPlayer(TETile[][] worldFrame) {
        int x, y;
        while (true) {
            x = RANDOM.nextInt(WIDTH);
            y = RANDOM.nextInt(HEIGHT);
            if (worldFrame[x][y].equals(Tileset.FLOOR)) {
                preObj = Tileset.FLOOR;
                worldFrame[x][y] = Tileset.PLAYER;
                xPos = x;
                yPos = y;
                break;
            }
        }
        while (true) {
            x = RANDOM.nextInt(WIDTH);
            y = RANDOM.nextInt(HEIGHT);
            if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) continue;
            if (worldFrame[x][y].equals(Tileset.WALL)
                    && (worldFrame[x][y - 1].equals(Tileset.NOTHING)
                            || worldFrame[x][y + 1].equals(Tileset.NOTHING)
                            || worldFrame[x - 1][y].equals(Tileset.NOTHING)
                            || worldFrame[x + 1][y].equals(Tileset.NOTHING))) {
                worldFrame[x][y] = Tileset.LOCKED_DOOR;
                break;
            }
        }
    }
}
