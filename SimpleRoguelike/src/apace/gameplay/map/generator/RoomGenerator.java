package apace.gameplay.map.generator;

import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Position;

public class RoomGenerator implements IMapGenerator {

	@Override
	public void generate(Map map, int startX, int startY, int level) {
		int w = map.getWidth();
		int h = map.getHeight();
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				map.setTile(new Position(x, y), Tiles.WALL);
			}
		}
		
		List<List<Position>> rooms = new LinkedList<>();
		List<Position> hallways = new LinkedList<>();
		
		for(int i = 0; i < 20; i++) {
			int rx = range(0, w - 1);
			int ry = range(0, h - 1);
			int rh = range(1, 6);
			int rw = range(1, 6);
			rooms.add(createRoom(map, rx, ry, rw, rh));
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
	
	private List<Position> createRoom(Map map, int x, int y, int w, int h) {
		List<Position> room = new LinkedList<>();
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Position p = new Position(x + i, y + j);
				if(map.isInBounds(p)) {
					room.add(p);
				}
			}
		}
		return room;
	}

	private int range(int min, int max) {
		return min + Logic.random.nextInt(max - min);
	}
	
}
