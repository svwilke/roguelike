package apace.gameplay.map.generator;

import java.util.ArrayList;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Direction;
import apace.utils.DistanceMap;
import apace.utils.Flags;
import apace.utils.Position;

public class StairGenerator implements IMapGenerator {
	
	int[] flags = new int[] {
			Integer.parseInt("01110101", 2),
			Integer.parseInt("10111010", 2),
			Integer.parseInt("11010011", 2),
			Integer.parseInt("11101100", 2)
	};
	int[] masks = new int[] {
			Integer.parseInt("00001010", 2),
			Integer.parseInt("00000101", 2),
			Integer.parseInt("00001100", 2),
			Integer.parseInt("00000011", 2)
	};
	
	int[] fallbackFlags = new int[] {
			Integer.parseInt("01010111", 2),
			Integer.parseInt("10011011", 2),
			Integer.parseInt("10101110", 2),
			Integer.parseInt("01101101", 2)
	};
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		ArrayList<Position> candidates = new ArrayList<>();
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Position p = new Position(x, y);
				if(map.getTile(p) == Tiles.DOOR && Flags.anycomp(map.getSurrounding(p), flags, masks)) {
					candidates.add(p);
				}
			}
		}
		if(candidates.size() > 0) {
			map.setTile(select(candidates), Tiles.STAIRS_UP);
			for(Position p : candidates) {
				if(map.getTile(p) != Tiles.STAIRS_UP) {
					map.setTile(p, Tiles.WALL);
				}
			}
		} else {
			for(int x = 0; x < map.getWidth(); x++) {
				for(int y = 0; y < map.getHeight(); y++) {
					Position p = new Position(x, y);
					if(map.getTile(p) == Tiles.FLOOR && Flags.anycomp(map.getSurrounding(p), fallbackFlags)) {
						candidates.add(p);
					}
				}
			}
			if(candidates.size() > 0) {
				map.setTile(select(candidates), Tiles.STAIRS_UP);
			}
		}
		
		Position p = new Position(startX, startY);
		// Code for removing doors nearby stairs
		/*for(int i = 0; i < 4; i++) {
			Position q = Direction.values()[i].from(p);
			if(map.isInBounds(q) && map.getTile(q) == Tiles.DOOR) {
				map.setTile(q, Tiles.FLOOR);
			}
		}*/
		map.setTile(p, Tiles.STAIRS_DOWN);
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}

}
