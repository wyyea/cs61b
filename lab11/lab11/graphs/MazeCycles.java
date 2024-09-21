package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    Maze maze;
    int s;
    int circle_point;
    boolean has_circle = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        marked[s] = true;
        announce();
    }

    public void dfs(int u, int parent){
        for (int v : maze.adj(u)){
            if(v == parent)
                continue;
            if(marked[v]){
                circle_point = v;
                has_circle = true;
                edgeTo[v] = u;
                announce();
                return;
            }
            
            marked[v] = true;
            announce();
            dfs(v, u);
            if(has_circle){
                if(u == circle_point)
                    has_circle = false;
                edgeTo[v] = u;
                announce();
                return;
            }
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s, -1);
    }

    // Helper methods go here
}

