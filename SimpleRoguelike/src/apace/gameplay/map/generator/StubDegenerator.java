package apace.gameplay.map.generator;

import java.util.LinkedList;
import java.util.List;

import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Flags;
import apace.utils.Position;

public class StubDegenerator implements IMapGenerator {

	@Override
	public void generate(Map map, int startX, int startY, int level) {
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
		List<Position> candidates = new LinkedList<>();
		do {
			for(Position p : candidates) {
				map.setTile(p, Tiles.WALL);
			}
			candidates.clear();
			for(int x = 0; x < map.getWidth(); x++) {
				for(int y = 0; y < map.getHeight(); y++) {
					Position p = new Position(x, y);
					if(map.getTile(p) == Tiles.FLOOR && Flags.anycomp(map.getSurrounding(p), flags, masks)) {
						candidates.add(p);
					}
				}
			}
			//if(Logic.random.nextInt(10) == 0) {
			//	break;
			//}
		} while(candidates.size() > 0);
		
	}

}
