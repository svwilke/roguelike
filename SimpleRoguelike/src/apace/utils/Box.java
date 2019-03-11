package apace.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Box implements Iterable<Position> {

	public int x;
	public int y;
	public int w;
	public int h;
	
	public Box(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int size() {
		return w * h;
	}
	
	public Box shrink(int n) {
		return new Box(x + n, y + n, w - n*2, h - n*2);
	}
	
	public Box expand(int n) {
		return new Box(x - n, y - n, w + n*2, h + n*2);
	}
	
	public boolean contains(Position p) {
		return p.getX() >= x && p.getX() < x + w && p.getY() >= y && p.getY() < y + h;
	}
	
	public List<Box> subtract(Box other) {
		List<Box> result = new LinkedList<Box>();
		List<Position> allResulting = new LinkedList<Position>();
		for(Position p : this) {
			if(!other.contains(p))
				allResulting.add(p);
		}

		for(Position p : allResulting) {
			Position right = p;
			Position down = p;
			if(anyContains(result, p)) {
				continue;
			}
			int w = 0, h = 0;
			while(allResulting.contains(right) && !anyContains(result, right)) {
				right = right.right();
				w++;
			}
			while(allResulting.contains(down) && !anyContains(result, down)) {
				down = down.down();
				h++;
			}
			result.add(new Box(p.getX(), p.getY(), w, h));
		}
		
		return result;
	}
	
	private static boolean anyContains(List<Box> boxes, Position p) {
		for(Box b : boxes) {
			if(b.contains(p))
				return true;
		}
		return false;
	}

	@Override
	public Iterator<Position> iterator() {
		List<Position> p = new LinkedList<Position>();
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				p.add(new Position(x + i, y + j));
			}
		}
		return p.iterator();
	}
	
	public static Box createBounding(Position...positions) {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for(Position p : positions) {
			minX = Math.min(minX, p.getX());
			minY = Math.min(minY, p.getY());
			maxX = Math.max(maxX, p.getX());
			maxY = Math.max(maxY, p.getY());
		}
		return new Box(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}
	
	public static Box createBounding(Iterable<Position> positions) {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for(Position p : positions) {
			minX = Math.min(minX, p.getX());
			minY = Math.min(minY, p.getY());
			maxX = Math.max(maxX, p.getX());
			maxY = Math.max(maxY, p.getY());
		}
		return new Box(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}
}
