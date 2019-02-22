package apace.lib;

import apace.gameplay.actor.ActorEnemy;

public class Enemies {

	public static ActorEnemy SLIME_SMALL = new ActorEnemy(Sprites.SLIME_SMALL, 1, 1);
	public static ActorEnemy SLIME_LARGE = new ActorEnemy(Sprites.SLIME_LARGE, 3, 1).setDelay(2);
	public static ActorEnemy GROO = new ActorEnemy(Sprites.GROO, 2, 2);
}
