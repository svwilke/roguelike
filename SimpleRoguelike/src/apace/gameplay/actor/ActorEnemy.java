package apace.gameplay.actor;

import apace.core.Game;
import apace.drawing.Sprite;
import apace.gameplay.map.Map;
import apace.process.IProcessable;
import apace.utils.Direction;
import apace.utils.DistanceMap;
import apace.utils.Position;

public class ActorEnemy extends ActorLiving {
	
	private static Position lastTarget;
	public static DistanceMap distanceMap;
	
	public ActorEnemy(Sprite sprite, int maxHp, int atkValue) {
		super(sprite);
		setMaxHealth(maxHp);
		setAttackValue(atkValue);
		distanceMap = new DistanceMap(Game.map);
	}

	public IProcessable takeTurn(Map map, ActorPlayer player) {
		if(lastTarget != player.getPosition()) {
			distanceMap.calculate(player.getPosition());
			lastTarget = player.getPosition();
		}
		//return tryMove(distanceMap.getBestMove(getPosition()), map);
		
		Direction[] directions = Direction.values();
		Direction bestMove = Direction.UP;
		int bestValue = distanceMap.getValue(position);
		//float bestValue = Float.POSITIVE_INFINITY;
		Position playerPos = player.getPosition();
		boolean hasMove = false;
		for(int i = 0; i < 4; i++) {
			Direction d = directions[i];
			Position newPos = d.from(position);
			//float dst = newPos.distanceSq(playerPos);
			int dst = distanceMap.getValue(newPos);
			if(isWalkableOrPlayer(map, player, newPos) && dst < bestValue) {
				bestValue = dst;
				bestMove = d;
				hasMove = true;
			}
		}
		if(hasMove) {
			return tryMove(bestMove, map);
		}
		return null;
	}
	
	private boolean isWalkableOrPlayer(Map map, ActorPlayer player, Position p) {
		return map.isWalkable(p) || map.getActor(p) == player;
	}
}
