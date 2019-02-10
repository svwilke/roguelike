package apace.gameplay.map;

import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.lib.Tiles;
import apace.utils.Position;

public class RoomGenerator implements IMapGenerator {

	@Override
	public void generate(Map map, int startX, int startY, int level) {
		List<List<Position>> rooms = new LinkedList<>();
		List<Position> hallways = new LinkedList<>();
		subdivide(1, 1, map.getWidth()-2, map.getHeight()-2, rooms, hallways, 0);
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				map.setTile(new Position(x, y), Tiles.WALL);
			}
		}
		for(List<Position> room : rooms) {
			for(Position p : room) {
				map.setTile(p, Tiles.FLOOR);
			}
		}
		for(Position p : hallways) {
			map.setTile(p, Tiles.FLOOR);
		}
		map.setTile(new Position(startX, startY), Tiles.STAIRS_DOWN);
	}
	
	private void subdivide(int x, int y, int w, int h, List<List<Position>> rooms, List<Position> hallway, int level) {
		int split;
		if(level == 1 || Logic.random.nextInt(10) == 0) {
			rooms.add(getRoom(x, y, w, h));
			return;
		}
		if(Logic.random.nextBoolean()) {
			if(w <= 4) {
				rooms.add(getRoom(x, y, w, h));
				return;
			}
			split = Logic.random.nextInt(w - 4) + 2;
			subdivide(x, y, split - 1, h, rooms, hallway, level + 1);
			subdivide(x + split, y, split - 1, h, rooms, hallway, level + 1);
			int hw = Logic.random.nextInt(h);
			hallway.add(new Position(x + split, hw));
		} else {
			if(h <= 4) {
				rooms.add(getRoom(x, y, w, h));
				return;
			}
			split = Logic.random.nextInt(h - 4) + 2;
			subdivide(x, y, w, split - 1, rooms, hallway, level + 1);
			subdivide(x, y + split, w, split - 1, rooms, hallway, level + 1);
			int hw = Logic.random.nextInt(w);
			hallway.add(new Position(hw, y + split));
		}
	}
	
	private List<Position> getRoom(int x, int y, int w, int h) {
		List<Position> room = new LinkedList<Position>();
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				room.add(new Position(x + i, y + j));
			}
		}
		return room;
	}

	
}
