package Question4;

public class SmartMouse extends Mouse {
    public SmartMouse(Maze maze, int delay, int startRow, int startCol) {
        super(maze, delay, startRow, startCol);
    }

    @Override
    protected void move() {
        boolean[][] visitedRooms = new boolean[maze.getNumRows()][maze.getNumCols()];
        dfsMove(row, col, visitedRooms);
    }

    private void dfsMove(int row, int col, boolean[][] visitedRooms) {
        if (row < 0 || col < 0 || row >= maze.getNumRows() || col >= maze.getNumCols() || visitedRooms[row][col])
            return;

        visitedRooms[row][col] = true;

        if(maze.isOpen(row, col, Direction.EAST)) {
            col++;
            dfsMove(row, col, visitedRooms); // go right
        } else if(maze.isOpen(row, col, Direction.WEST)) {
            col--;
            dfsMove(row, col,visitedRooms); //go left
        } else if(maze.isOpen(row, col, Direction.SOUTH)) {
            row++;
            dfsMove(row, col,visitedRooms); //go down
        } else if(maze.isOpen(row, col, Direction.NORTH)) {
            row--;
            dfsMove(row, col,visitedRooms); // go up
        }
    }
}
