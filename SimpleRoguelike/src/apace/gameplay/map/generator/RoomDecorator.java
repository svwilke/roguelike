package apace.gameplay.map.generator;

import java.util.LinkedList;
import java.util.List;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.lib.Tiles;
import apace.utils.Box;
import apace.utils.Position;

public class RoomDecorator implements IMapGenerator {

	private List<Room> rooms = new LinkedList<Room>();
	
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		identifyRooms(map);
		for(Room room : rooms) {
			int r = Logic.random.nextInt(room.w * room.h);
			for(Position p : room) {
				if(r <= 0) {
					Tile t = Tiles.FLOOR;
					switch(room.getType()) {
					case SECRET: t = Tiles.BOMB; break;
					case STAIRSDOWN: t = Tiles.STAIRS_DOWN; break;
					case STAIRSUP: t = Tiles.STAIRS_UP; break;
					case DEADEND: t = Tiles.CHEST_LARGE; break;
					case JUNCTION: t = Tiles.CARPET; break;
					case PASSTHRU: t = Tiles.VASE_SMALL; break;
					case PASSBY: t = Tiles.CHEST_SMALL; break;
					case UNIDENTIFIED: t = Tiles.WALL; break;
					}
					map.setTile(p, t);
					break;
				}
				r--;
			}
		}
	}
	
	private void identifyRooms(Map map) {
		rooms.clear();
		Box box = new Box(0, 0, 1, 1);
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Position tl = new Position(x, y);
				if(map.isWalkable(tl)) {
					Position right = tl;
					Position down = tl;
					int w = 1, h = 1;
					if(!map.isWalkable(tl.right()) || !map.isWalkable(tl.down())) {
						continue;
					}
					while(map.isWalkable(right.right())) {
						right = right.right();
						w++;
					}
					while(map.isWalkable(down.down())) {
						down = down.down();
						h++;
					}
					box.x = x;
					box.y = y;
					box.w = w;
					box.h = h;
					if(checkRoom(map, box)) {
						rooms.add(new Room(map, box.x, box.y, box.w, box.h));
					}
				}
			}
		}
	}
	
	private boolean checkRoom(Map map, Box box) {
		for(Position p : box) {
			if(!map.isWalkable(p)) {
				return false;
			}
			for(Room r : rooms) {
				if(r.contains(p)) {
					return false;
				}
			}
		}
		return true;
	}

}
