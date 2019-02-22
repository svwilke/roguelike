package apace.ai;

import apace.gameplay.actor.Actor;
import apace.gameplay.map.Map;
import apace.process.IProcessable;

public abstract class AI {

	protected Actor actor;
	
	public AI(Actor actor) {
		this.actor = actor;
	}
	
	public abstract IProcessable perform(Map map);
}
