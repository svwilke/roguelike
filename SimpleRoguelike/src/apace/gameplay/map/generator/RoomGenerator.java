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
		
		List<Room> rooms = new LinkedList<>();
		List<List<Position>> walkable = new LinkedList<>();
		List<Position> hallways = new LinkedList<>();
		
		for(int i = 0; i < 100; i++) {
			int rx = range(0, w - 1);
			int ry = range(0, h - 1);
			int rh = range(1, 6);
			int rw = range(1, 6);
			Room roomCandidate = new Room(rx, ry, rw, rh);
			if(available(roomCandidate, rooms)) {
				rooms.add(roomCandidate);
				walkable.add(createRoom(map, roomCandidate));
			}
			
		}
		for(List<Position> room : walkable) {
			for(Position p : room) {
				map.setTile(p, Tiles.FLOOR);
			}
		}
		for(Position p : hallways) {
			map.setTile(p, Tiles.FLOOR);
		}
		map.setTile(new Position(startX, startY), Tiles.STAIRS_DOWN);
	}
	
	private List<Position> createRoom(Map map, Room r) {
		List<Position> room = new LinkedList<>();
		for(int i = 0; i < r.w - 1; i++) {
			for(int j = 0; j < r.h - 1; j++) {
				Position p = new Position(r.x + i, r.y + j);
				if(map.isInBounds(p)) {
					room.add(p);
				}
			}
		}
		return room;
	}
	
	private boolean available(Room room, List<Room> rooms) {
		int fw, fh;
		for(Room otherRoom : rooms) {
			fw = Math.max(otherRoom.x + otherRoom.w, room.x + room.w) - Math.min(otherRoom.x, room.x);
			fh = Math.max(otherRoom.y + otherRoom.h, room.y + room.h) - Math.min(otherRoom.y, room.y);
			if(!(fw >= room.w + otherRoom.w) || (fh >= room.h + otherRoom.h)) {
				return false;
			}
		}
		return true;
	}
	
	private class Room {
		public int x;
		public int y;
		public int w;
		public int h;
		public Room(int x, int y, int w, int h) {
			this.x = x; this.y = y; this.w = w; this.h = h;
		}
	}

	private int range(int min, int max) {
		return min + Logic.random.nextInt(max - min);
	}
	
}
