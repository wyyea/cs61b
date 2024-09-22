package hw4.puzzle;
import java.util.PriorityQueue;
import java.util.Vector;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solver {
    public class pair{
        public WorldState worldstate;
        public int value;
        public pair(WorldState w, int v){
            worldstate = w;
            value = v;
        }
    }

    private int moves;
    private Vector<WorldState> solution;
    private Set<WorldState> reached;
    HashMap<WorldState, Integer> hurMap;
    HashMap<WorldState, Integer> disMap;
    HashMap<WorldState, WorldState> edgeTo;

    public Solver(WorldState initial){
        moves = 0;
        solution = new Vector<WorldState>();

        if(initial.isGoal()){
            solution.add(initial);
            return;
        }

        reached = new HashSet<WorldState>();
        hurMap = new HashMap<WorldState,Integer>(); 
        disMap = new HashMap<WorldState,Integer>(); 
        edgeTo = new HashMap<>();
        hurMap.put(initial, initial.estimatedDistanceToGoal());
        disMap.put(initial, 0);
        edgeTo.put(initial, initial);

        PriorityQueue<pair> q = new PriorityQueue<>(new custom_comparator());
        q.add(new pair(initial, 0)); // 总是第一个取出，设置为0也可

        while(!q.isEmpty()){
            pair tmpPair = q.poll();
            WorldState tmpState = tmpPair.worldstate;

            if(reached.contains(tmpState))
                continue;

            if(tmpState.isGoal()){
                moves = disMap.get(tmpState);
                WorldState prevState = edgeTo.get(tmpState);
                while(tmpState != prevState){
                    solution.add(0, tmpState);
                    tmpState = prevState;
                    prevState = edgeTo.get(prevState);
                } 
                solution.add(0, tmpState);
                return;
            }
            
            reached.add(tmpState);
            int dis = disMap.get(tmpState) + 1;
            WorldState grandpState = edgeTo.get(tmpState);

            for (WorldState nextState: tmpState.neighbors()){
                if(nextState.equals(grandpState))
                    continue;
                
                int hur;
                if(disMap.containsKey(nextState)){
                    int olddis = disMap.get(nextState);
                    if(olddis <= dis)
                        continue;
                    hur = hurMap.get(nextState);
                }else{
                    hur = nextState.estimatedDistanceToGoal();
                    hurMap.put(nextState, hur);
                }
                // the dis should be updated or never be set
                disMap.put(nextState, dis);
                edgeTo.put(nextState, tmpState);
                q.add(new pair(nextState, dis + hur));
            }
        }
    }

    private class custom_comparator implements Comparator<pair>{
        @Override
        public int compare(pair a, pair b){
            return a.value - b.value;
        }
    }

    public int moves(){
        return moves;
    }

    public Iterable<WorldState> solution(){
        return solution;
    }
}
