package Question4;

import java.awt.*;

public abstract class Mouse implements Runnable {
    protected int row;
    protected int col;
    protected Maze maze;
    protected boolean stopRequested;
    protected int delay;

    public Mouse(Maze maze, int delay, int startRow, int startCol) {
        this.maze = maze;
        this.delay = delay;
        this.row = startRow;
        this.col = startCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected abstract void move();

    @Override
    public void run() {
        while (!stopRequested) {
            move();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawMouse(Graphics g, int width, int height) {
        int diameter = 10;
        int radius = diameter / 2;

        int numCols = maze.getNumCols();
        int numRows = maze.getNumRows();

        int endX = width / numCols;
        int endZ = height / numRows;

        int startX = 5 + col * endX;
        int startZ = 5 + row * endZ;

        g.fillOval(startX + endX / 2 - radius, startZ + endZ / 2 - radius, diameter, diameter);
    }
}
