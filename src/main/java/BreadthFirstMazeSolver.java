
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tjmelanson
 */
public class BreadthFirstMazeSolver implements MazeSolver {
    private Square _current;
    private Square _goal;
    private static boolean _foundGoal = false;
    private int _iterations; // For benchmarking
    
    private static int[][] _ACTIONS = {{1,0}, {0,1}};
    private static int[][] _costs;
    private Square[][] _mapCache;
    private Queue<Square> _availableSquares = new LinkedList<Square>();

    @Override
    public void initialize(Square source, Square goal, Square[][] map) {
        this._goal = goal;
        _mapCache = new Square[map.length][map[0].length];
        _costs = new int[map.length][map[0].length];
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length; i++) {
                _costs[j][i] = 0;
            }
        }
        System.arraycopy(map, 0, this._mapCache, 0, map.length);
        _availableSquares.add(source);
        _iterations=0;
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
        Square current;
        do {
            current = _availableSquares.remove();
        } while (current.visited);
        
        current.visited = true;
        _mapCache[current.i][current.j].visited = true;
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
            if (_mapCache[newJ][newI].occupied || _mapCache[newJ][newI].visited) {
                continue;
            }
            _costs[newJ][newI] = _costs[current.j][current.i] + 1;
            _availableSquares.add(_mapCache[newJ][newI]);
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
                if (_costs[newJ][newI] == _costs[backTrack.j][backTrack.i] - 1) {
                    backTrack = _mapCache[newJ][newI];
                    path.add(0, backTrack);
                    break;
                }
            }
        }
        return path;
    }
}
