
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tjmelanson
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        MazeDrawing canvas = new MazeDrawing();
        canvas.initializeMap();
        canvas.setSize(MazeDrawing.WIDTH, MazeDrawing.HEIGHT);
        Square begin = new Square(0, 0, false, false);
        Square end = new Square(20, 20, false, false);
        
        
        // Create maze solver.
        MazeSolver solver = new BreadthFirstMazeSolver();
        solver.initialize(begin, end, canvas.map);
        canvas.createMazeSolver(solver);
        
        // Add the canvas to the frame.
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        
        // Add the timer
        Timer timer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                canvas.repaint();
            }
        });
        timer.start();
        
        frame.setVisible(true);
    }
}
