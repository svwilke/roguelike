package apace.gameplay.map;

import java.awt.Graphics2D;

import apace.drawing.Palette;
import apace.gameplay.IInteractable;
import apace.gameplay.actor.Actor;
import apace.lib.Sprites;
import apace.lib.Tiles;
import apace.process.IProcessable;
import apace.utils.Flags;
import apace.utils.Position;

public class TileDoor extends Tile implements IInteractable {

	public TileDoor() {
		super(Sprites.DOOR_HORIZONTAL, false, true);
		this.setClear(false);
	}
	
	@Override
	public void render(Graphics2D g, Map map, Palette p, Position pos) {
		int bitfield = map.getSurrounding(pos);
		int comp = Integer.parseInt("00110000", 2);
		int mask = Integer.parseInt("00001111", 2);
		super.render(g, p, Flags.comp(bitfield, comp, mask) ? Sprites.DOOR_VERTICAL : Sprites.DOOR_HORIZONTAL, pos);
	}

	@Override
	public IProcessable interact(Map map, Actor actor, Position position) {
		map.setTile(position, Tiles.FLOOR);
		return null;
	}
}
