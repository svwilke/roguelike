package apace.utils;

import apace.gameplay.map.Map;

public class DistanceMap {

	private Map map;
	private int width;
	private int height;
	private int[][] distanceMap;
	
	public DistanceMap(Map map) {
		this.map = map;
		width = map.getWidth();
		height = map.getHeight();
		distanceMap = new int[width][height];
	}
	
	public int getValue(Position p) {
		if(map.isInBounds(p)) {
			int v = distanceMap[p.getX()][p.getY()];
			if(v >= 0) {
				return v;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public Direction getBestMove(Position p) {
		if(map.isInBounds(p)) {
			int min = Integer.MAX_VALUE;
			Direction best = Direction.UP;
			for(int i = 0; i < 4; i++) {
				Direction d = Direction.values()[i];
				Position q = d.from(p);
				if(map.isInBounds(q)) {
					if(distanceMap[q.getX()][q.getY()] < min) {
						min = distanceMap[q.getX()][q.getY()];
						best = d;
					}
				}
			}
			return best;
		}
		System.err.println("DistanceMap#getBestMove called with invalid position (either on target or out of bounds).");
		return Direction.UP;
	}
	
	public void calculate(Position target) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				distanceMap[i][j] = -1;
			}
		}
		spread(target.getX(), target.getY(), 0);
	}
	
	private void spread(int x, int y, int v) {
		if(distanceMap[x][y] == -1 || distanceMap[x][y] > v) {
			distanceMap[x][y] = v;
			for(int i = 0; i < 4; i++) {
				Direction d = Direction.values()[i];
				Position p = new Position(x + d.getX(), y + d.getY());
				if(map.isInBounds(p)) {
					int newV = v + getScore(p);
					spread(p.getX(), p.getY(), newV);
				}
			}
		}
	}
	
	private int getScore(Position p) {
		if(map.isWalkable(p)) {
			return 1;
		}
		if(map.hasActor(p)) {
			return 10;
		}
		return 100;
	}
}
