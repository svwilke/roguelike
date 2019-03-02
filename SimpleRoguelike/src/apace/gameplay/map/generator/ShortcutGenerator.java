package apace.gameplay.map.generator;

import java.util.ArrayList;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.DistanceMap;
import apace.utils.Flags;
import apace.utils.Position;

public class ShortcutGenerator implements IMapGenerator {

	private int[] flags = new int[] {
			Integer.parseInt("11000000", 2),
			Integer.parseInt("00110000", 2)
	};
	
	private int[] masks = new int[] {
			Integer.parseInt("00001111", 2),
			Integer.parseInt("00001111", 2)
	};
	
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		DistanceMap dist = new DistanceMap(map);
		ArrayList<Position> candidates = new ArrayList<>();
		int minDist = 20;
		do {
			dist.calculate(new Position(startX, startY));
			if(candidates.size() > 0) {
				map.setTile(select(candidates), Tiles.DOOR);
			}
			candidates.clear();
			for(int x = 0; x < map.getWidth(); x++) {
				for(int y = 0; y < map.getHeight(); y++) {
					Position p = new Position(x, y);
					if(map.getTile(p) == Tiles.WALL && Flags.anycomp(map.getSurrounding(p), flags, masks)) {
						if(map.isWalkable(p.up())) {
							dist.calculate(p.up());
							if(dist.getValue(p.down()) >= minDist) {
								candidates.add(p);
							}
						} else
						if(map.isWalkable(p.left())) {
							dist.calculate(p.left());
							if(dist.getValue(p.right()) >= minDist) {
								candidates.add(p);
							}
						}
						
					}
				}
			}
		} while(candidates.size() > 0);
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}
}
