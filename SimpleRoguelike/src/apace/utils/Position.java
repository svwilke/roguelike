package apace.utils;

import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.lib.Reference;

public class Position {

	private int x;
	private int y;
	private int offsetX = 0;
	private int offsetY = 0;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(Position p) {
		this(p.x, p.y);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public void setOffset(int offsetX, int offsetY) {
		setOffsetX(offsetX);
		setOffsetY(offsetY);
	}
	
	public int getOffsetX() {
		return this.offsetX;
	}
	
	public int getOffsetY() {
		return this.offsetY;
	}
	
	public int getPixelX() {
		return getOffsetX() + getX() * Reference.TILE_SIZE;
	}
	
	public int getPixelY() {
		return getOffsetY() + getY() * Reference.TILE_SIZE;
	}
	
	public Position left() {
		return new Position(x - 1, y);
	}
	
	public Position right() {
		return new Position(x + 1, y);
	}
	
	public Position up() {
		return new Position(x, y - 1);
	}
	
	public Position down() {
		return new Position(x, y + 1);
	}
	
	public Direction getDirection() {
		int ax = Math.abs(x);
		int ay = Math.abs(y);
		if(ax > ay) {
			return Direction.get((int)Math.signum(x), 0);
		} else
		if(ay > ax) {
			return Direction.get(0, (int)Math.signum(y));
		}
		return Direction.get((int)Math.signum(x), (int)Math.signum(y));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Position) {
			return o.hashCode() == hashCode();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public static Position random(int boundX, int boundY) {
		return new Position(Logic.random.nextInt(boundX), Logic.random.nextInt(boundY));
	}
	
	public Position add(Position p) {
		return new Position(x + p.x, y + p.y);
	}
	
	public Position subtract(Position p) {
		return new Position(x - p.x, y - p.y);
	}
	
	public float distanceSq(Position p) {
		float dx = (float)(p.x - x);
		float dy = (float)(p.y - y);
		return dx * dx + dy * dy;
	}
	
	public int distanceManhattan(Position p) {
		return p.x - x + p.y - y;
	}
	
	public List<Position> lineTo(Position p) {
		List<Position> line = new LinkedList<Position>();
		int x1, y1, x2, y2, sx, sy, dx, dy;
		x1 = x; y1 = y; x2 = p.x; y2 = p.y;
		if(x1 < x2) {
			sx = 1;
			dx = x2 - x1;
		} else {
			sx = -1;
			dx = x1 - x2;
		}
		if(y1 < y2) {
			sy = 1;
			dy = y2 - y1;
		} else {
			sy = -1;
			dy = y1 - y2;
		}
		int err, e2;
		err = dx - dy;
		line.add(new Position(x1, y1));
		while(x1 != x2 || y1 != y2) {
			e2 = err * 2;
			if(e2 > -dy) {
				err -= dy;
				x1 += sx;
			}
			if(e2 < dx) {
				err += dx;
				y1 += sy;
			}
			line.add(new Position(x1, y1));
		}
		return line;
	}
}
