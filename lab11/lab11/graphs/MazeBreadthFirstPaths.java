package lab11.graphs;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        marked[s] = true;
        distTo[s] = 0;
        edgeTo[s] = s;
        announce();
        
        while(!q.isEmpty()){
            int u = q.poll(), dis = distTo[u] + 1;
            for (int v: maze.adj(u)){
                if(marked[v])
                    continue;
                marked[v] = true;
                distTo[v] = dis;
                edgeTo[v] = u;
                announce();
                q.add(v);
                
                if(v == t)
                    return;
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

