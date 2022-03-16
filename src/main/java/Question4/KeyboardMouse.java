package Question4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardMouse extends Mouse implements KeyListener {

    private Direction direction;

    public KeyboardMouse(Maze maze, int delay, int startRow, int startCol) {
        super(maze, delay, startRow, startCol);
    }

    protected void move() {
        if(direction == null) return;
        if(maze.isOpen(row, col, direction)) {
            if(direction == Direction.NORTH) {
                this.row--;
            }
            if(direction == Direction.SOUTH) {
                this.row++;
            }
            if(direction == Direction.EAST) {
                this.col++;
            }
            if(direction == Direction.WEST) {
                this.col--;
            }
        }
        direction = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//      W
        if(e.getKeyCode() == 87) {
            direction = Direction.NORTH;
        }
//      A
        if(e.getKeyCode() == 65) {
            direction = Direction.SOUTH;
        }
//      S
        if(e.getKeyCode() == 83) {
            direction = Direction.WEST;
        }
//      D
        if(e.getKeyCode() == 68) {
            direction = Direction.EAST;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
