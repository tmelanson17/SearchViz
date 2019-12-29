import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class MazeDrawing extends Canvas {
    private boolean _stopped = false;
    
    private static int ROWS = 50;
    private static int COLS = 50;
    public static int HEIGHT = 250;
    public static int WIDTH = 250;
    
    // TODO: Share this between interfaces
    private Square end = new Square(20, 20, false, false);
    
    
    public Square[][] map = new Square[COLS][ROWS];
    private int[][] _occupiedGridCells = {{5,5}, {5,6}, {5,7}, {5,8}, {5,9}, {5,10},
        {15, 15}, {15, 16}, {15, 17}, {15, 18}, {15, 19}, {15, 20}, {15, 21}};
    
    public MazeSolver solver;
    
    public void initializeMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                map[j][i] = new Square(i, j, false, false);
            }
        }
        for (int coordIndex = 0; coordIndex < _occupiedGridCells.length; ++coordIndex) {
            int i = _occupiedGridCells[coordIndex][1];
            int j = _occupiedGridCells[coordIndex][0];
            map[j][i].occupied = true;
        }
    }
    
    public void createMazeSolver(MazeSolver solver) {
        this.solver = solver;
    }
    
    public void update(Graphics g) {
        if (_stopped) {
            return;
        }
        int squareHeight = HEIGHT / ROWS;
        int squareWidth = WIDTH / COLS;
        // Update the map with the action.
        Square next = solver.getNextAction();
        if (map[next.j][next.i].occupied) {
            _stopped = true;
            throw new RuntimeException("Grid is occupied at square (" + Integer.toString(next.j) 
                    + ", " + Integer.toString(next.i) + ")");
        }
        map[next.j][next.i] = next;
        
        // Draw on the canvas
        
        if (solver.reachedGoal()) {
            g.setColor(Color.green);
            for (Square s : solver.backTrack()) {
                g.fillRect(s.j*squareWidth, s.i*squareHeight, squareWidth, squareHeight);
            }
        } else {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {  
                    g.setColor(Color.black);
                    g.drawRect(j*squareWidth, i*squareHeight, 
                            squareWidth, squareHeight);
                    if (i == next.i && j == next.j) {
                        g.setColor(Color.yellow);
                    } else if (map[j][i].occupied) {
                        g.setColor(Color.black);
                    } else if (map[j][i].visited) {
                        g.setColor(Color.blue);
                    } else if (i == end.i && j == end.j) {
                        g.setColor(Color.red);
                    } else {  
                        g.setColor(Color.white);
                    }
                    g.fillRect(j*squareWidth, i*squareHeight, 
                            squareWidth, squareHeight);

                }
            }
        }
    }
    
}
