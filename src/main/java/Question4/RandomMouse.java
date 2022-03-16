package Question4;

public class RandomMouse extends Mouse {

    public RandomMouse(Maze maze, int delay, int startRow, int startCol) {
        super(maze, delay, startRow, startCol);
    }

    @Override
    protected void move() {
        Direction direction = getRandomDirection();
        if(maze.isOpen(row, col, direction)) {
            if(direction == Direction.NORTH) row++;
            if(direction == Direction.SOUTH) row--;
            if(direction == Direction.EAST) col++;
            if(direction == Direction.WEST) col--;
        }
    }

    private Direction getRandomDirection() {
        int random = (int) (Math.random() * 4);
        if(random == 0) return Direction.NORTH;
        else if(random == 1) return Direction.SOUTH;
        else if(random == 2) return Direction.WEST;
        else return Direction.EAST;
    }
}
