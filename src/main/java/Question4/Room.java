package Question4;

public class Room {
    private boolean northDoorOpen;
    private boolean eastDoorOpen;
    private boolean southDoorOpen;
    private boolean westDoorOpen;

    public Room() {
        this.northDoorOpen = false;
        this.eastDoorOpen = false;
        this.southDoorOpen = false;
        this.westDoorOpen = false;
    }

    public void openDoor(Direction door) {
        if(door == (Direction.NORTH)) northDoorOpen = true;
        if(door == (Direction.EAST)) eastDoorOpen = true;
        if(door == (Direction.SOUTH)) southDoorOpen = true;
        if(door == (Direction.WEST)) westDoorOpen = true;
    }

    public boolean hasOpenDoor() {
        if(northDoorOpen || eastDoorOpen || southDoorOpen || westDoorOpen) return true;
        return false;
    }

    public boolean isDoorOpen(Direction direction) {
        if(direction == (Direction.NORTH)) return northDoorOpen;
        if(direction == (Direction.EAST)) return eastDoorOpen;
        if(direction == (Direction.SOUTH)) return southDoorOpen;
        if(direction == (Direction.WEST)) return westDoorOpen;
        return false;
    }
}
