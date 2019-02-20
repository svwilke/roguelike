package apace.lib;

import apace.gameplay.ITurnTaker;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.gameplay.map.TileChest;
import apace.gameplay.map.TileDoor;
import apace.gameplay.map.TileProcess;
import apace.gameplay.map.TileStairs;
import apace.gameplay.map.TileWall;
import apace.process.IProcessable;
import apace.utils.Direction;
import apace.utils.Position;

public class Tiles {

	public static Tile FLOOR = new Tile(Sprites.FLOOR, true, false, false);
	public static TileWall WALL = new TileWall();
	public static TileChest CHEST_LARGE = new TileChest(Sprites.CHEST_CLOSED_LARGE, Sprites.CHEST_OPEN_LARGE);
	public static TileChest CHEST_SMALL = new TileChest(Sprites.CHEST_CLOSED_SMALL, Sprites.CHEST_OPEN_SMALL);
	public static TileStairs STAIRS_UP = new TileStairs();
	public static Tile STAIRS_DOWN = new Tile(Sprites.STAIRS_DOWN, true, false);
	public static TileDoor DOOR = new TileDoor();
	public static Tile VASE_SMALL = new Tile(Sprites.VASE_SMALL, false, false);
	public static Tile BOMB = new TileProcess(false, false, new ITurnTaker() {

		@Override
		public IProcessable takeTurn(Map map, Position position) {
			for(int i = 0; i < 8; i++) {
				Position p = Direction.values()[i].from(position);
				if(map.isInBounds(p)) {
					map.setTile(p, FLOOR);
				}
			}
			map.setTile(position, FLOOR);
			return null;
		}
		
	}, Sprites.BOMB_0, Sprites.BOMB_1, Sprites.BOMB_2, Sprites.BOMB_3);
}
