package apace.gameplay.map;

import apace.gameplay.IInteractable;
import apace.gameplay.actor.Actor;
import apace.lib.Sprites;
import apace.lib.Tiles;
import apace.process.IProcessable;
import apace.utils.Position;

public class TileDoor extends Tile implements IInteractable {

	public TileDoor() {
		super(Sprites.DOOR, false, true);
	}

	@Override
	public IProcessable interact(Map map, Actor actor, Position position) {
		map.setTile(position, Tiles.FLOOR);
		return null;
	}
}
