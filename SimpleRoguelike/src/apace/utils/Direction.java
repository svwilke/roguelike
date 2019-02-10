package apace.utils;

public enum Direction {
	LEFT, RIGHT, UP, DOWN, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;
	
	private int dx;
	private int dy;
	private Direction opposite;
	
	static {
		LEFT.dx = -1;
		LEFT.dy = 0;
		LEFT.opposite = RIGHT;
		RIGHT.dx = 1;
		RIGHT.dy = 0;
		RIGHT.opposite = LEFT;
		UP.dx = 0;
		UP.dy = -1;
		UP.opposite = DOWN;
		DOWN.dx = 0;
		DOWN.dy = 1;
		DOWN.opposite = UP;
		UP_LEFT.dx = -1;
		UP_LEFT.dy = -1;
		UP_LEFT.opposite = DOWN_RIGHT;
		UP_RIGHT.dx = 1;
		UP_RIGHT.dy = -1;
		UP_RIGHT.opposite = DOWN_LEFT;
		DOWN_LEFT.dx = -1;
		DOWN_LEFT.dy = 1;
		DOWN_LEFT.opposite = UP_RIGHT;
		DOWN_RIGHT.dx = 1;
		DOWN_RIGHT.dy = 1;
		DOWN_RIGHT.opposite = UP_LEFT;
	}
	
	public static Direction get(int dx, int dy) {
		for(Direction d : Direction.values()) {
			if(d.dx == dx && d.dy == dy) {
				return d;
			}
		}
		throw new IllegalArgumentException("No direction with dx=" + dx + " and dy=" + dy + " found.");
	}
	
	public Direction getOpposite() {
		return opposite;
	}
	
	public int getX() {
		return dx;
	}
	
	public int getY() {
		return dy;
	}
	
	public Position toPosition() {
		return new Position(dx, dy);
	}
	
	public Position from(Position position) {
		return new Position(position.getX() + dx, position.getY() + dy);
	}
}
