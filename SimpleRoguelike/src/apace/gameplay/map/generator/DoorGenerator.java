package apace.gameplay.map.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import apace.core.Logic;
import apace.gameplay.map.Map;
import apace.lib.Tiles;
import apace.utils.Direction;
import apace.utils.Flags;
import apace.utils.Position;

public class DoorGenerator implements IMapGenerator {

	private int[] doorFlags = new int[] {
			Integer.parseInt("11001100", 2),
			Integer.parseInt("11000011", 2),
			Integer.parseInt("00110101", 2),
			Integer.parseInt("00111010", 2)
	};
	
	private int[] doorMasks = new int[] {
			Integer.parseInt("00000011", 2),
			Integer.parseInt("00001100", 2),
			Integer.parseInt("00001010", 2),
			Integer.parseInt("00000101", 2)
	};
	
	int[][] areas;
	@Override
	public void generate(Map map, int startX, int startY, int level) {
		ArrayList<Position> candidates = new ArrayList<>();
		calcAreas(map, candidates);
		
		HashMap<Set<Integer>, ArrayList<Position>> doorCandidates = new HashMap<>();
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
		for(ArrayList<Position> ps : doorCandidates.values()) {
			Position doorPos = select(ps);
			map.setTile(doorPos, Tiles.DOOR);
		}
		
		candidates.clear();
		int[] deadEndFlags = new int[] {
				Integer.parseInt("01111111", 2),
				Integer.parseInt("10111111", 2),
				Integer.parseInt("11011111", 2),
				Integer.parseInt("11101111", 2)
		};
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Position p = new Position(x, y);
				if(Flags.anycomp(map.getSurrounding(p), deadEndFlags)) {
					candidates.add(p);
				}
			}
		}
		if(candidates.size() > 0) {
			map.setTile(select(candidates), Tiles.STAIRS_UP);
		}
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
