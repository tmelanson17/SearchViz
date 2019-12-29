/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tjmelanson
 */
public class Square {
    int i;
    int j;
    boolean occupied;
    boolean visited;
    
    Square(int i, int j, boolean occupied, boolean visited) {
        this.i = i;
        this.j = j;
        this.occupied = occupied;
        this.visited = visited;
    }
}
