package apace.lib;

import apace.gameplay.map.Tile;
import apace.gameplay.map.TileChest;
import apace.gameplay.map.TileDoor;
import apace.gameplay.map.TileStairs;
import apace.gameplay.map.TileWall;

public class Tiles {

	public static Tile FLOOR = new Tile(Sprites.FLOOR, true, false, false);
	public static TileWall WALL = new TileWall();
	public static TileChest CHEST_LARGE = new TileChest(Sprites.CHEST_CLOSED_LARGE, Sprites.CHEST_OPEN_LARGE);
	public static TileChest CHEST_SMALL = new TileChest(Sprites.CHEST_CLOSED_SMALL, Sprites.CHEST_OPEN_SMALL);
	public static TileStairs STAIRS_UP = new TileStairs();
	public static Tile STAIRS_DOWN = new Tile(Sprites.STAIRS_DOWN, true, false);
	public static TileDoor DOOR = new TileDoor();
	public static Tile VASE_SMALL = new Tile(Sprites.VASE_SMALL, false, false);
}
