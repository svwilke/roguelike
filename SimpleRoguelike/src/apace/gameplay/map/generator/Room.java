package apace.gameplay.map.generator;

import java.util.LinkedList;
import java.util.List;

import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Box;
import apace.utils.Direction;
import apace.utils.Position;

public class Room extends Box {

	private Map map;
	private List<Door> doors;
	private Type type;
	
	public Room(Map map, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.map = map;
		type = Type.UNIDENTIFIED;
		identify();
	}
	
	public List<Door> getDoors() {
		return doors;
	}
	
	public Type getType() {
		return type;
	}

	private void identify() {
		doors = new LinkedList<Door>();
		for(Position p : this) {
			for(int i = 0; i < 4; i++) {
				Direction d = Direction.values()[i];
				Position pd = d.from(p);
				if(map.isInBounds(pd) && map.getTile(pd) == Tiles.DOOR) {
					Door door = new Door();
					door.position = pd;
					door.direction = d;
					doors.add(door);
				} else
				if(map.isInBounds(pd) && map.getTile(pd) == Tiles.STAIRS_UP) {
					type = Type.STAIRSUP;
				}
			}
			if(map.getTile(p) == Tiles.STAIRS_DOWN) {
				type = Type.STAIRSDOWN;
			}
			if(map.getTile(p) == Tiles.STAIRS_UP) {
				type = Type.STAIRSUP;
			}
		}
		if(type == Type.UNIDENTIFIED) {
			if(doors.size() > 2) {
				type = Type.JUNCTION;
			} else
			if(doors.size() == 1) {
				type = Type.DEADEND;
			} else
			if(doors.size() == 0) {
				type = Type.SECRET;
			} else
			if(doors.size() == 2) {
				Position p0 = doors.get(0).direction.getOpposite().from(doors.get(0).position);
				Position p1 = doors.get(1).direction.getOpposite().from(doors.get(1).position);
				if(Box.createBounding(p0, p1).size() < size() / 2) {
					type = Type.PASSBY;
				} else {
					type = Type.PASSTHRU;
				}
			}
		}
	}
	
	public class Door {
		public Position position;
		public Direction direction;
	}
	
	public enum Type {
		UNIDENTIFIED, PASSTHRU, PASSBY, JUNCTION, DEADEND, STAIRSUP, STAIRSDOWN, SECRET
	}
}
