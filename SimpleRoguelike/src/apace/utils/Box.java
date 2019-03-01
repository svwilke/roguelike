package apace.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Box {

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
	
	public Iterator<Position> enumerate() {
		List<Position> p = new LinkedList<Position>();
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				p.add(new Position(x + i, y + j));
			}
		}
		return p.iterator();
	}
	
	public Box shrink(int n) {
		return new Box(x + n, y + n, w - n*2, h - n*2);
	}
	
	public Box expand(int n) {
		return new Box(x - n, y - n, w + n*2, h + n*2);
	}
}
