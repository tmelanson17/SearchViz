
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author tjmelanson
 */
public class AStarMazeSolver implements MazeSolver {
    private class SolverSquare {
        int costToSquare;
        Square square;
        
        SolverSquare(int cost, Square s) {
            this.costToSquare = cost;
            this.square = s;
        }
    };
    
    private class SolverSquareComparator implements Comparator<SolverSquare> {
        public int compare(SolverSquare a, SolverSquare b) {
            return a.costToSquare - b.costToSquare;
        }
    }
    private Square _current;
    private Square _goal;
    private static boolean _foundGoal = false;
    private int _iterations=0;
    
    private static int[][] _ACTIONS = {{1,0}, {0,1}}; //, {1,1}};
    private Square[][] _mapCache;
    private int[][] _heuristic;
    private int[][] _costs;
    
    // TODO: This might not be the best solution.
    private int[][] _move;
    
    private Queue<SolverSquare> _availableSquares = 
            new PriorityQueue<SolverSquare>(11, new SolverSquareComparator());


    @Override
    public void initialize(Square source, Square goal, Square[][] map) {
        this._goal = goal;
        _mapCache = new Square[map.length][map[0].length];
        _move = new int[map.length][map[0].length];
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                _move[j][i] = -1;
            }
        }
        _heuristic = new int[map.length][map[0].length];
        for (int j = 0; j < map.length; ++j) {
            for (int i = 0; i < map[0].length; ++i) {
                int diffI = Math.abs(i - goal.i);
                int diffJ = Math.abs(j - goal.j);
            
                _heuristic[j][i] = diffI * diffI + diffJ * diffJ;
            }
        }
        _costs = new int[map.length][map[0].length];
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                _costs[j][i] = 1000;
            }
        }
        System.arraycopy(map, 0, this._mapCache, 0, map.length);
        _costs[source.j][source.i] = 0;
        _availableSquares.add(new SolverSquare(_costs[source.j][source.i] + 
                _heuristic[source.j][source.i], source));
    }

    @Override
    public Square getNextAction() {
        if (_availableSquares.isEmpty()) {
            throw new NullPointerException("No more options avaiable.");
        }
        if (_foundGoal) {
            return _goal;
        }
        _iterations++;
        Square current = _availableSquares.remove().square;
        current.visited = true;
        _mapCache[current.j][current.i].visited = true;
        _move[current.j][current.i] = _iterations;
        if (current.i == _goal.i && current.j == _goal.j) {
            System.out.println("Found goal.");
            System.out.println("Iterations: " + Integer.toString(_iterations));
            _foundGoal = true;
            return current;
        }
        for (int actionIndex = 0; actionIndex < _ACTIONS.length; ++actionIndex) {
            int newI = current.i + _ACTIONS[actionIndex][1];
            int newJ = current.j + _ACTIONS[actionIndex][0];
            if (newI < 0 || newI >= _mapCache[0].length || 
                    newJ < 0 || newJ >= _mapCache.length) {
                continue;
            }
            if (_mapCache[newJ][newI].occupied) {
                continue;
            }
            if (_costs[newJ][newI] <= _costs[current.j][current.i] + 1) {
                continue;
            }
            _costs[newJ][newI] = _costs[current.j][current.i] + 1;
            _availableSquares.add(new SolverSquare(_costs[newJ][newI] + _heuristic[newJ][newI],
                    _mapCache[newJ][newI]));
        }
        return current;
    }
    
    @Override
    public boolean reachedGoal() {
        return _foundGoal;
    }
    
    @Override
    public ArrayList<Square> backTrack() {
        ArrayList<Square> path = new ArrayList<Square>();
        path.add(0, _goal);
        Square backTrack = path.get(0);
        while (_costs[backTrack.j][backTrack.i] > 0) {
            for (int actionIndex = 0; actionIndex < _ACTIONS.length; ++actionIndex) {
                int newI = backTrack.i - _ACTIONS[actionIndex][1];
                int newJ = backTrack.j - _ACTIONS[actionIndex][0];
                if (newI < 0 || newI >= _mapCache[0].length || 
                        newJ < 0 || newJ >= _mapCache.length) {
                    continue;
                }
                // If the cost is the same, we found the previous value.
                if (_move[newJ][newI] == _move[backTrack.j][backTrack.i] - 1) {
                    backTrack = _mapCache[newJ][newI];
                    path.add(0, backTrack);
                    break;
                }
            }
        }
        return path;
    }
}
