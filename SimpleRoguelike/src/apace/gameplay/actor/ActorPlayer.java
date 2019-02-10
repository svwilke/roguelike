package apace.gameplay.actor;

import apace.lib.Sprites;

public class ActorPlayer extends ActorLiving {
	
	public ActorPlayer() {
		super(Sprites.PLAYER);
		setAttackValue(2);
		setMaxHealth(5);
	}
}
