package apace.gameplay.map.generator;

import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Position;

public class CarpetRoomDecorator implements IRoomDecorator {

	@Override
	public void generate(Map map, Room room) {
		for(Position p : room.getDoorSafePositions()) {
			map.setTile(p, Tiles.CARPET);
		}
	}

}
