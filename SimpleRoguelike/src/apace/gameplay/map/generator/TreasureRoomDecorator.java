package apace.gameplay.map.generator;

import apace.gameplay.map.Map;
import apace.gameplay.map.generator.Room.Form;
import apace.lib.Tiles;
import apace.utils.Direction;
import apace.utils.Position;
import apace.utils.Utils;

public class TreasureRoomDecorator implements IRoomDecorator {

	@Override
	public void generate(Map map, Room room) {
		Position treasurePos = null;
		/*if(room.getForm() == Form.DEADEND) {
			Room.Door d = room.getDoors().get(0);
			int xm = room.x + room.w / 2;
			int ym = room.y + room.h / 2;
			if(d.direction == Direction.UP || d.direction == Direction.DOWN) {
				if(d.direction == Direction.UP) {
					ym = room.y + room.h / 2 + 1;
				}
				if(d.position.getX() == xm) {
					treasurePos = new Position(xm, ym);
				} else {
					int steps = 0;
					int delta = (xm - d.position.getX()) > 0 ? 1 : -1;
					int x = d.position.getX();
					while(x != delta) {
						x += delta;
						steps++;
					}
					x += steps * delta;
					treasurePos = new Position(x, ym);
				}
			} else {
				if(d.direction == Direction.LEFT) {
					xm = room.x + room.w / 2 + 1;
				}
				if(d.position.getY() == ym) {
					treasurePos = new Position(xm, ym);
				} else {
					int steps = 0;
					int delta = (ym - d.position.getY()) > 0 ? 1 : -1;
					int y = d.position.getY();
					while(y != delta) {
						y += delta;
						steps++;
					}
					y += steps * delta;
					treasurePos = new Position(xm, y);
				}
			}
		} else {*/
			treasurePos = Utils.choice(room.getDoorSafePositions());
//		}
		map.setTile(treasurePos, Tiles.CHEST_LARGE);
	}

}
