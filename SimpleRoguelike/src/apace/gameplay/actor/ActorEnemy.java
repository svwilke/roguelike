package apace.gameplay.actor;

import apace.drawing.Sprite;
import apace.gameplay.map.Map;
import apace.utils.Direction;
import apace.utils.Position;

public class ActorEnemy extends ActorLiving {

	public ActorEnemy(Sprite sprite, int maxHp, int atkValue) {
		super(sprite);
		setMaxHealth(maxHp);
		setAttackValue(atkValue);
	}

	public Direction takeTurn(Map map, ActorPlayer player) {
		Direction[] directions = Direction.values();
		Direction bestMove = Direction.UP;
		float bestValue = Float.POSITIVE_INFINITY;
		Position playerPos = player.getPosition();
		boolean hasMove = false;
		for(int i = 0; i < 4; i++) {
			Direction d = directions[i];
			Position newPos = d.from(position);
			float dst = newPos.distanceSq(playerPos);
			if(isWalkableOrPlayer(map, player, newPos) && dst < bestValue) {
				bestValue = dst;
				bestMove = d;
				hasMove = true;
			}
		}
		return bestMove;
	}
	
	private boolean isWalkableOrPlayer(Map map, ActorPlayer player, Position p) {
		return map.isWalkable(p) || map.getActor(p) == player;
	}
}
