package apace.gameplay.map.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.gameplay.map.TileWall;
import apace.lib.Tiles;
import apace.utils.Flags;
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
		int maxSize = 6;
		int minSize = 3;
		ArrayList<Position> candidates = new ArrayList<Position>(w * h);
		List<Room> rooms = new LinkedList<Room>();
		for(int r = 0; r < 30; r++) {
			int rw = Logic.random.nextInt(maxSize - minSize) + minSize;
			int rh = Logic.random.nextInt(maxSize - minSize) + minSize;
			Room currentRoom = new Room(0, 0, rw, rh);
			candidates.clear();
			for(int x = 0; x < w - rw; x++) {
				for(int y = 0; y < h - rh; y++) {
					currentRoom.x = x;
					currentRoom.y = y;
					if(available(currentRoom, map)) {
						candidates.add(new Position(x, y));
					}
				}
			}
			if(candidates.size() > 0) {
				Position p = select(candidates);
				currentRoom.x = p.getX();
				currentRoom.y = p.getY();
				//rooms.add(currentRoom);
				createRoom(map, currentRoom);
			}
		}
		map.setTile(new Position(startX, startY), Tiles.STAIRS_DOWN);
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}
	
	private void createRoom(Map map, Room r) {
		for(int x = r.x; x <= r.x + r.w; x++) {
			for(int y = r.y; y <= r.y + r.h; y++) {
				Position p = new Position(x, y);
				if(map.isInBounds(p)) {
					map.setTile(p, Tiles.FLOOR);
				}
			}
		}
	}
	
	private int[] carvableFlags = new int[] {
		Integer.parseInt("11111111", 2),
		Integer.parseInt("01111111", 2),
		Integer.parseInt("10111111", 2),
		Integer.parseInt("11011111", 2),
		Integer.parseInt("11101111", 2)
	};
	
	private int[] carvableMasks = new int[] {
		Integer.parseInt("00000000", 2),
		Integer.parseInt("00001010", 2),
		Integer.parseInt("00000101", 2),
		Integer.parseInt("00001100", 2),
		Integer.parseInt("00000011", 2)
	};
	
	private int availableFlag = 255;
	private boolean available(Room room, Map map) {
		for(int x = room.x; x <= room.x + room.w; x++) {
			for(int y = room.y; y <= room.y + room.h; y++) {
				Position p = new Position(x, y);
				Tile t = map.getTile(p);
				if(t instanceof TileWall) {
					TileWall tw = (TileWall)t;
					int flag = tw.getSurrounding(map, p);
					if(flag != availableFlag) {
						return false;
					}
				} else {
					return false;
				}
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
