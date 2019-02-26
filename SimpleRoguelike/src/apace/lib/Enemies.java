package apace.lib;

import apace.gameplay.actor.ActorEnemy;
import apace.gameplay.actor.ActorLiving;

public class Enemies {

	public static ActorLiving SLIME_SMALL = new ActorEnemy(Sprites.SLIME_SMALL, 1, 1).setDisplayName("Small Slime");
	public static ActorLiving SLIME_LARGE = new ActorEnemy(Sprites.SLIME_LARGE, 3, 1).setDelay(2).setDisplayName("Large Slime");
	public static ActorLiving GROO = new ActorEnemy(Sprites.GROO, 2, 2).setDisplayName("Groo");
}
