package Question4;

import java.awt.*;

public class Maze {
    private int numRows;
    private int numCols;
    private Room[][] room;

    public Maze() {
        this.numRows = 10;
        this.numCols = 10;
        this.room = new Room[10][10];
        addRoomsInRoomArray();
    }

    public Maze(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.room = new Room[numRows][numCols];
        addRoomsInRoomArray();
    }

    private void addRoomsInRoomArray() {
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numCols; j++) {
                room[i][j] = new Room();
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public boolean isOpen(int row, int col, Direction direction) {
        if(isInsideMaze(row, col)) return room[row][col].isDoorOpen(direction);
        return false;
    }

    public boolean isInsideMaze(int row, int col) {
        if(row >= 0 && row < numRows && col >= 0 && col < numCols) return true;
        return false;
    }

    public boolean hasOpenDoor(int row, int col) {
        if(isInsideMaze(row, col)) return room[row][col].hasOpenDoor();
        return false;
    }

    public void openDoor(int row, int col, Direction direction) {
        if(isInsideMaze(row, col)) {
            this.room[row][col].openDoor(direction);
            if(direction == Direction.NORTH && row > 0) this.room[row-1][col].openDoor(Direction.SOUTH);
            if(direction == Direction.SOUTH && row < numRows-1) this.room[row+1][col].openDoor(Direction.NORTH);
            if(direction == Direction.WEST && col > 0) this.room[row][col-1].openDoor(Direction.EAST);
            if(direction == Direction.EAST && col < numCols-1) this.room[row][col+1].openDoor(Direction.WEST);
        }
    }

    public void draw(Graphics g, int worldwidth, int worldheight) {
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numCols; j++) {
                if(!isOpen(i, j, Direction.NORTH)) g.drawLine((worldwidth/numRows)*i, (worldheight/numCols)*j, (worldwidth/numRows)*(i+1), (worldheight/numCols)*j);
                if(!isOpen(i, j, Direction.EAST)) g.drawLine((worldwidth/numRows)*(i+1), (worldheight/numCols)*j, (worldwidth/numRows)*(i+1), (worldheight/numCols)*(j+1));
                if(!isOpen(i, j, Direction.SOUTH)) g.drawLine((worldwidth/numRows)*(i), (worldheight/numCols)*(j+1), (worldwidth/numRows)*(i+1), (worldheight/numCols)*(j+1));
                if(!isOpen(i, j, Direction.WEST)) g.drawLine((worldwidth/numRows)*(i), (worldheight/numCols)*j, (worldwidth/numRows)*(i), (worldheight/numCols)*(j+1));
            }
        }
    }
}
