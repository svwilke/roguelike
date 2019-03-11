package apace.gameplay.map.generator;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.lib.Tiles;
import apace.utils.Position;

public class EmptyRoomDecorator implements IRoomDecorator{

	@Override
	public void generate(Map map, Room room) {
		if(Logic.random.nextFloat() < 0.6f) {
			Tile t = Logic.random.nextFloat() < 0.2f ? Tiles.HOLE : Tiles.CARPET;
			for(Position p : room.shrink(1)) {
				map.setTile(p, t);
			}
		}
	}
	
}
