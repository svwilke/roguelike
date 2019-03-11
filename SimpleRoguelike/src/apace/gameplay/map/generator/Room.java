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
	private Form form;
	
	public Room(Map map, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.map = map;
		form = Form.UNIDENTIFIED;
		identify();
	}
	
	public List<Door> getDoors() {
		return doors;
	}
	
	public Form getForm() {
		return form;
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
				if(form == Form.UNIDENTIFIED && map.isInBounds(pd) && map.getTile(pd) == Tiles.STAIRS_UP) {
					form = Form.STAIRSUP;
				} else
				if(form == Form.UNIDENTIFIED && map.isInBounds(pd) && map.getTile(pd) == Tiles.STAIRS_DOWN) {
					form = Form.STAIRSDOWN;
				}
			}
			if(form == Form.UNIDENTIFIED && map.getTile(p) == Tiles.STAIRS_DOWN) {
				form = Form.STAIRSDOWN;
			}
			if(form == Form.UNIDENTIFIED && map.getTile(p) == Tiles.STAIRS_UP) {
				form = Form.STAIRSUP;
			}
		}
		if(form == Form.UNIDENTIFIED) {
			if(doors.size() > 2) {
				form = Form.JUNCTION;
			} else
			if(doors.size() == 1) {
				form = Form.DEADEND;
			} else
			if(doors.size() == 0) {
				form = Form.SECRET;
			} else
			if(doors.size() == 2) {
				Position p0 = doors.get(0).direction.getOpposite().from(doors.get(0).position);
				Position p1 = doors.get(1).direction.getOpposite().from(doors.get(1).position);
				Box doorBounds = Box.createBounding(p0, p1);
				if(doorBounds.size() <= size() / 2 && subtract(doorBounds).size() == 1) {
					form = Form.PASSBY;
				} else {
					form = Form.PASSTHRU;
				}
			}
		}
	}
	
	public List<Position> getDoorSafePositions() {
		List<Position> doorInsides = new LinkedList<Position>();
		for(Door d : doors) {
			doorInsides.add(d.direction.getOpposite().from(d.position));
		}
		Box doorBounds = Box.createBounding(doorInsides);
		List<Box> result = subtract(doorBounds);
		List<Position> allPositions = new LinkedList<Position>();
		for(Box b : result) {
			for(Position p : b) {
				allPositions.add(p);
			}
		}
		return allPositions;
	}
	
	public class Door {
		public Position position;
		public Direction direction;
	}
	
	public enum Form {
		UNIDENTIFIED, PASSTHRU, PASSBY, JUNCTION, DEADEND, STAIRSUP, STAIRSDOWN, SECRET
	}
}
