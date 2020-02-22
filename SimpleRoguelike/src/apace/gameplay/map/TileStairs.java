package apace.gameplay.map;

import apace.core.Logic;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorPlayer;
import apace.lib.Sprites;
import apace.process.ScheduledCall;
import apace.process.WaitFrames;
import apace.utils.Position;

public class TileStairs extends Tile {

	public TileStairs() {
		super(Sprites.STAIRS_UP, true, false);
	}
	
	@Override
	public void onTileEnter(Map map, Position position, Actor actor) {
		if(actor instanceof ActorPlayer) {
			Logic.push(new ScheduledCall(() -> {map.generate(position.getX(), position.getY()); map.addActor(position, actor);}));
			Logic.push(new WaitFrames(20));
		}
	}
}
