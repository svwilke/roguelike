package apace.gameplay.map.generator;

import java.util.ArrayList;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Direction;
import apace.utils.Flags;
import apace.utils.Position;

public class HallwayGenerator implements IMapGenerator {

	private int[] carvableFlags = new int[] {
			Integer.parseInt("11111111", 2),
			Integer.parseInt("01111111", 2),
			Integer.parseInt("10111111", 2),
			Integer.parseInt("11011111", 2),
			Integer.parseInt("11101111", 2)
		};
		
		private int[] carvableMasks = new int[] {
			Integer.parseInt("00000000", 2),
			Integer.parseInt("00001010", 2),
			Integer.parseInt("00000101", 2),
			Integer.parseInt("00001100", 2),
			Integer.parseInt("00000011", 2)
		};
		
	
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		worm(map, new Position(startX, startY));
		ArrayList<Position> wormStarts = getAllWithFlag(map, 255);
		while(wormStarts.size() > 0) {
			Position start = select(wormStarts);
			worm(map, start);
			wormStarts = getAllWithFlag(map, 255);
		}
	}
	
	private ArrayList<Position> getAllWithFlag(Map map, int flag) {
		ArrayList<Position> list = new ArrayList<>(map.getWidth() * map.getHeight());
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Position p = new Position(x, y);
				if(!map.isWalkable(p) && map.getSurrounding(p) == flag) {
					list.add(p);
				}
			}
		}
		return list;
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}
	
	int[] d = new int[] {0, 1, 2, 3};
	private void worm(Map map, Position p) {
		if(isCarvable(map, p)) {
			map.setTile(p, Tiles.FLOOR);
			int[] dIndices = shuffled(d);
			for(int i = 0; i < 4; i++) {
				Direction d = Direction.values()[dIndices[i]];
				Position pn = d.from(p);
				if(map.isInBounds(pn) && !map.isWalkable(pn)) {
					worm(map, pn);
					//return;
				}
			}
		}
	}
	
	private int[] shuffled(int[] a) {
		int[] s = a.clone();
		int tmp;
		for(int i = 0; i < s.length - 1; i++) {
			int r = Logic.random.nextInt(s.length - 1 - i);
			tmp = s[r];
			s[r] = s[i];
			s[i] = tmp;
		}
		return s;
	}
	
	private boolean isCarvable(Map map, Position p) {
		int flag = map.getSurrounding(p);
		for(int i = 0; i < carvableFlags.length; i++) {
			if(Flags.comp(flag, carvableFlags[i], carvableMasks[i])) {
				return true;
			}
		}
		return false;
		
	}

}
