package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {
        "You can do this!",
        "I believe in you!",
        "You got this!",
        "You're a star!",
        "Go Bears!",
        "Too easy for you!",
        "Wow, so impressive!"
    };

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        // TODO: Generate random string of letters of length n
        char[] list = new char[n];
        for (int i = 0; i < n; i++) list[i] = CHARACTERS[rand.nextInt(26)];
        return new String(list);
    }

    public void drawFrame(String s, String round, String command, String encourage) {
        // TODO: Take the string and display it in the center of the screen
        // TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(round.length() / 2, height - 1, round);
        StdDraw.text(width / 2, height - 1, command);
        StdDraw.text(width - encourage.length() / 2, height - 1, encourage);
        StdDraw.line(0, height - 2, width, height - 2);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters, String round, String command, String encourage) {
        // TODO: Display each character in letters, making sure to blank the screen between letters
        StdDraw.clear(Color.BLACK);
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1), round, command, encourage);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n, String round, String command, String encourage) {
        // TODO: Read n letters of player input
        char[] list = new char[n];
        for (int i = 0; i < n; i++) {
            while (!StdDraw.hasNextKeyTyped()) {}
            list[i] = StdDraw.nextKeyTyped();
            drawFrame(new String(list), round, command, encourage);
        }
        return new String(list);
    }

    public void startGame() {
        // TODO: Set any relevant variables before the game starts
        int n = 5, r = 1;
        // TODO: Establish Game loop
        while (true) {
            String round = "Round: " + r;
            String command = "Watch!";
            String encourage = ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
            drawFrame(round, round, command, encourage);
            StdDraw.pause(1000);
            String std = generateRandomString(n);
            flashSequence(std, round, command, encourage);
            command = "Type!";
            drawFrame("", round, command, encourage);
            String input = solicitNCharsInput(n, round, command, encourage);
            StdDraw.pause(500);
            if (!std.equals(input)) {
                drawFrame("Game Over! You made it to round: " + r, round, command, encourage);
                break;
            }
            r++;
        }
    }
}
