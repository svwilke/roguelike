package apace.ai;

import apace.core.Game;
import apace.gameplay.actor.Actor;
import apace.gameplay.map.Map;
import apace.process.IProcessable;

public class AIEnemy extends AI {

	public AIEnemy(Actor actor) {
		super(actor);
	}

	@Override
	public IProcessable perform(Map map) {
		if(map.isLineOfSight(actor.getPosition(), Game.player.getPosition())) {
			
		}
		return null;
	}

	
}
