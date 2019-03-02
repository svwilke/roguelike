package apace.gameplay.map.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.gameplay.map.TileWall;
import apace.lib.Tiles;
import apace.utils.Box;
import apace.utils.Position;

public class RoomGenerator implements IMapGenerator {

	private List<Box> rooms;
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		rooms = new LinkedList<Box>();
		int w = map.getWidth();
		int h = map.getHeight();
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				map.setTile(new Position(x, y), Tiles.WALL);
			}
		}
		int maxSize = 6;
		int minSize = 2;
		int maxRooms = 5;
		ArrayList<Position> candidates = new ArrayList<Position>(w * h);
		for(int r = 0; r < 10; r++) {
			int rw = Logic.random.nextInt(maxSize - minSize) + minSize;
			int rh = Logic.random.nextInt(maxSize - minSize) + minSize;
			Box currentRoom = new Box(0, 0, rw, rh);
			candidates.clear();
			if(r == 0) {
				for(int x = Math.max(0, startX - rw); x < Math.min(w - rw, startX + 1); x++) {
					for(int y = Math.max(0, startY - rh); y < Math.min(h - rh, startY + 1); y++) {
						currentRoom.x = x;
						currentRoom.y = y;
						if(available(currentRoom, map)) {
							candidates.add(new Position(x, y));
						}
					}
				}
			} else {
				for(int x = 0; x < w - rw; x++) {
					for(int y = 0; y < h - rh; y++) {
						currentRoom.x = x;
						currentRoom.y = y;
						if(available(currentRoom, map)) {
							candidates.add(new Position(x, y));
						}
					}
				}
			}
			if(candidates.size() > 0) {
				Position p = select(candidates);
				currentRoom.x = p.getX();
				currentRoom.y = p.getY();
				createRoom(map, currentRoom, Tiles.FLOOR);
				if(Logic.random.nextInt(10) == 0) {
					if(Logic.random.nextInt(3) == 0) {
						createRoom(map, currentRoom.shrink(1), Tiles.HOLE);
					} else {
						createRoom(map, currentRoom.shrink(1), Tiles.CARPET);
					}
				}
				rooms.add(currentRoom);
				maxRooms--;
				if(maxRooms <= 0) {
					break;
				}
			}
		}
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}
	
	private void createRoom(Map map, Box r, Tile t) {
		for(int x = r.x; x <= r.x + r.w; x++) {
			for(int y = r.y; y <= r.y + r.h; y++) {
				Position p = new Position(x, y);
				if(map.isInBounds(p)) {
					map.setTile(p, t);
				}
			}
		}
	}
	
	
	private int availableFlag = 255;
	private boolean available(Box room, Map map) {
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
}
