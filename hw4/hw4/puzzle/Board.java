package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;
import java.lang.IndexOutOfBoundsException;

public class Board implements WorldState{
    
    private int[][] tiles;
    private int N;
    final private static int BLANK = 0;
    final private int tileMax;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][];
        for (int i = 0; i < N; i++)
            this.tiles[i] = tiles[i].clone();
        tileMax = N * N - 1;
    }

    public int tileAt(int i, int j){ 
        int ret = tiles[i][j];
        if(ret < 0 || ret > tileMax)
            throw new IndexOutOfBoundsException();
        return ret;
    }

    public int size(){ return N; }

    // from josh hug
    @Override
    public Iterable<WorldState> neighbors(){
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming(){
        int error = 0, correctTile = 1;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++, correctTile++)
                if(tiles[i][j] != 0 && tiles[i][j] != correctTile)
                    error++;
        return error;
    }

    public int manhattan(){
        int dis = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(tiles[i][j] == 0)
                    continue;
                int tile = tiles[i][j] - 1;
                int x = tile / N, y = tile % N;
                dis += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return dis;
    }

    @Override
    public int estimatedDistanceToGoal(){ return manhattan(); }

    public boolean equals(Object y){
        if(!(y instanceof Board))
            return false;

        Board yboard = (Board) y;
        if(yboard.size() != N)
            return false;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if(yboard.tileAt(i, j) != tiles[i][j])
                    return false;
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public int hashCode(){
        return tiles.hashCode();
    }
}
