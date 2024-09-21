package lab11.graphs;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX, targetY;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        // return -1;
        return Math.abs(targetX - maze.toX(v)) + Math.abs(targetY - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        PriorityQueue<Integer> q = new PriorityQueue<>(new custom_comparator());
        marked[s] = true;
        q.add(s);
        announce();

        while(!q.isEmpty()){
            int u = q.poll(), dis = distTo[u] + 1;
            for (int v: maze.adj(u)){
                if(marked[v])
                    continue;
                
                marked[v] = true;
                distTo[v] = dis + 1;
                edgeTo[v] = u;
                announce();
                q.add(v);
                
                if(v == t)
                    return;
            }
        }
    }

    private class custom_comparator implements Comparator<Integer>{
        @Override
        public int compare(Integer a, Integer b){
            return distTo[a] + h(a) - distTo[b] - h(b);
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

