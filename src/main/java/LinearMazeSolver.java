
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tjmelanson
 */
public class LinearMazeSolver implements MazeSolver {
    private Square start;
    private Square goal;
    private static int numIterations = 100;
    private int currIteration;
    
    @Override
    public void initialize(Square start, Square goal, Square[][] map) {
        this.start = new Square(start.i, start.j, start.occupied, start.visited);
        this.goal = new Square(goal.i, goal.j, goal.occupied, goal.visited);
        this.currIteration = 0;
    }

    @Override
    public Square getNextAction() {
        int deltaI = goal.i - start.i;
        int deltaJ = goal.j - start.j;
        float progress = Float.valueOf(currIteration) / Float.valueOf(numIterations);
        int iNext = Math.round(progress * deltaI);
        int jNext = Math.round(progress * deltaJ);
        Square out = new Square(iNext, jNext, false, true);
        this.currIteration = (this.currIteration+1) % numIterations;
        
        return out; 
    }

    @Override
    public boolean reachedGoal() {
        return false;
    }

    @Override
    public ArrayList<Square> backTrack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
