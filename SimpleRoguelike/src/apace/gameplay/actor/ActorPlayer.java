package apace.gameplay.actor;

import apace.lib.Sprites;

public class ActorPlayer extends ActorLiving {
	
	public ActorPlayer() {
		super(Sprites.PLAYER);
		setAttackValue(1);
		setMaxHealth(5);
		setDisplayName("Player");
	}
}
