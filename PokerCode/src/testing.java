/* Name: Rowan Lawler
   Date: 10/22/21
   Assignment: Cafe Wall Project
   Description: This program creates lines of figure with white and black squares, & blue x's.
   It also creates grids of these figures, with offsets and space for mortar in between each
   row. The size of each square is scalable, as well as the mortar room.                   */

import java.awt.*;  // Color Library
public class testing {
    public static final int MORTAR = 2;  //mortar
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(650, 400);
        panel.setBackground(Color.GRAY);
        Graphics g = panel.getGraphics();
        drawSquares(g,0,0,20,4);
        drawSquares(g,50,70,30,5);
        drawGrid(g,10,150,25,0,4);
        drawGrid(g,250,200,25,10,3);
        drawGrid(g,425,180,20,10,5);
        drawGrid(g,400,20,35,35,2);
    }


    // Draws pairs of black squares with blue x's and white squares,
// taking the top left x,y coords, the size of each box, and the # of pairs
    public static void drawSquares(Graphics g,int x,int y,int boxSize,int pairs) {
        for (int i = 0; i <pairs*2; i++) {
            g.setColor(Color.BLACK);
            for(int j = 0; j < i%2; j=5) {
                g.setColor(Color.WHITE);
            }
            g.fillRect(x + i*boxSize,y,boxSize,boxSize);
            for(int j = 1; j > i%2; j=-5) {
                g.setColor(Color.BLUE);
                g.drawLine(x + i*boxSize,y,x + i*boxSize + boxSize,y+boxSize);
                g.drawLine(x + i*boxSize,y+boxSize,x + i*boxSize +boxSize,y);
            }

        }
    }
    // Creates a grid of drawSquares figures, taking the top left x,y coords of grid,
    // the size of each box, the offset of every 2 rows in pixels, and the # of pairs.
    // A space for mortar is included between each row, and can be sized with the MORTAR constant.
    public static void drawGrid(Graphics g,int x,int y,int boxSize,int offset,int pairs) {
        for (int i = 1; i <=pairs*2; i++) {
            for(int j = 1; j > i%2; j=-5) {
                x = x + offset;
            }
            drawSquares(g,x,y,boxSize,pairs);
            for(int j = 1; j > i%2; j=-5) {
                x = x - offset;
            }
            y = y + MORTAR + boxSize;
        }
    }
}
