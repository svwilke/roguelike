package apace.gameplay.map.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import apace.gameplay.map.Map;
import apace.utils.Box;
import apace.utils.Position;
import apace.utils.Utils;

public class RoomClassifier implements IMapGenerator {

	private List<Room> rooms = new LinkedList<Room>();
	
	private HashMap<Room.Form, RoomType[]> formTypeMap = new HashMap<>();
	private HashMap<RoomType, IRoomDecorator> decoratorMap = new HashMap<>();
	
	public RoomClassifier() {
		formTypeMap.put(Room.Form.DEADEND, new RoomType[] {RoomType.TREASURE});
		formTypeMap.put(Room.Form.SECRET, new RoomType[] {RoomType.TREASURE});
		formTypeMap.put(Room.Form.PASSBY, new RoomType[] {RoomType.EMPTY, RoomType.EMPTY, RoomType.VASES});
		formTypeMap.put(Room.Form.PASSTHRU, new RoomType[] {RoomType.MONSTER, RoomType.EMPTY});
		formTypeMap.put(Room.Form.JUNCTION, new RoomType[] {RoomType.MONSTER, RoomType.MONSTER, RoomType.MONSTER, RoomType.EMPTY});
		formTypeMap.put(Room.Form.STAIRSUP, new RoomType[] {RoomType.EMPTY});
		formTypeMap.put(Room.Form.STAIRSDOWN, new RoomType[] {RoomType.VASES, RoomType.VASES, RoomType.EMPTY});
		formTypeMap.put(Room.Form.UNIDENTIFIED, new RoomType[] {RoomType.EMPTY});
		
		decoratorMap.put(RoomType.TREASURE, new TreasureRoomDecorator());
		decoratorMap.put(RoomType.MONSTER, new MonsterRoomDecorator());
		decoratorMap.put(RoomType.EMPTY, new EmptyRoomDecorator());
		decoratorMap.put(RoomType.VASES, new VaseRoomDecorator());
	}
	
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		identifyRooms(map);
		for(Room room : rooms) {
			RoomType type = Utils.choice(formTypeMap.get(room.getForm()));
			decoratorMap.get(type).generate(map, room);
			//new CarpetRoomDecorator().generate(map, room);
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
