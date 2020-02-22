package apace.gameplay.map.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Direction;
import apace.utils.DistanceMap;
import apace.utils.Position;

public class DoorGenerator implements IMapGenerator {
	
	int[][] areas;
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		ArrayList<Position> candidates;
		HashMap<Set<Integer>, ArrayList<Position>> doorCandidates = new HashMap<>();
		//do {
			candidates = new ArrayList<>();
			calcAreas(map, candidates);
			doorCandidates.clear();
			for(Position p : candidates) {
				int l = area(p.left());
				int r = area(p.right());
				int u = area(p.up());
				int d = area(p.down());
				if(l > 0 && r > 0 && r != l) {
					Set<Integer> con = new HashSet<Integer>();
					con.add(l);
					con.add(r);
					if(!doorCandidates.containsKey(con)) {
					ArrayList<Position> list = new ArrayList<>();
						list.add(p);
						doorCandidates.put(con, list);
					} else {
						doorCandidates.get(con).add(p);
					}
					continue;
				}
				if(u > 0 && d > 0 && u != d) {
					Set<Integer> con = new HashSet<Integer>();
					con.add(u);
					con.add(d);
					if(!doorCandidates.containsKey(con)) {
						ArrayList<Position> list = new ArrayList<>();
						list.add(p);
						doorCandidates.put(con, list);
					} else {
						doorCandidates.get(con).add(p);
					}
					continue;
				}
			}
			DistanceMap dist = new DistanceMap(map);
			//if(doorCandidates.size() > 0) {
			//	int r = Logic.random.nextInt(doorCandidates.size());
				for(ArrayList<Position> ps : doorCandidates.values()) {
			//		if(r <= 0) {
						Position doorPos = select(ps);
						Position p0, p1;
						if(map.isWalkable(doorPos.up())) {
							p0 = doorPos.up();
							p1 = doorPos.down();
						} else {
							p0 = doorPos.right();
							p1 = doorPos.left();
						}
						dist.calculate(p0);
						if(dist.getValue(p1) > 12) 
							map.setTile(doorPos, Tiles.DOOR);
			//			break;
			//		} else {
			//			r--;
			//		}
				}
			//}
			
		//} while(doorCandidates.size() > 0);
		candidates.clear();
	}
	
	@SuppressWarnings("unused")
	private int distance(Map map, Position p0, Position p1) {
		DistanceMap dm = new DistanceMap(map);
		dm.calculate(p0);
		return dm.getValue(p1);
	}
	
	private void calcAreas(Map map, ArrayList<Position> candidates) {
		areas = new int[map.getWidth()][map.getHeight()];
		int id = 1;
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Position p = new Position(x, y);
				if(areas[x][y] == 0 && map.isWalkable(p)) {
					floodfill(map, p, id);
					id++;
				} else {
					if(!map.isWalkable(p) && areas[x][y] == 0) {
						candidates.add(p);
					}
				}
			}
		}
	}
	
	private Position select(ArrayList<Position> positions) {
		return positions.get(Logic.random.nextInt(positions.size()));
	}
	
	private int area(Position p) {
		if(p.getX() < 0 || p.getY() < 0 || p.getX() >= areas.length || p.getY() >= areas[0].length) {
			return 0;
		}
		return areas[p.getX()][p.getY()];
	}
	
	private void floodfill(Map map, Position p, int id) {
		if(areas[p.getX()][p.getY()] == 0 && (map.isWalkable(p) || map.getTile(p) == Tiles.DOOR)) {
			areas[p.getX()][p.getY()] = id;
			for(int i = 0; i < 4; i++) {
				Position pn = Direction.values()[i].from(p);
				if(map.isInBounds(pn))
					floodfill(map, pn, id);
			}
		}
	}

}
