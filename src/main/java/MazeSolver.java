
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
public interface MazeSolver {
    public void initialize(Square source, Square goal, Square[][] map);
    public Square getNextAction();
    public boolean reachedGoal();
    public ArrayList<Square> backTrack();
}
