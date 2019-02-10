package apace.gameplay.map;

import java.awt.Color;

import apace.core.Logic;
import apace.drawing.Splash;
import apace.drawing.Sprite;
import apace.drawing.Window;
import apace.gameplay.IInteractable;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorLiving;
import apace.lib.Reference;
import apace.process.IProcessable;
import apace.utils.Position;
import apace.utils.StringHelper;

public class TileChest extends Tile implements IInteractable {
	
	private Tile openChestTile;
	
	public TileChest(Sprite sprite, Sprite open) {
		super(sprite, false, false);
		openChestTile = new Tile(open, false, false);
	}

	@Override
	public IProcessable interact(Map map, Actor actor, Position position) {
		map.setTile(position, openChestTile);
		String item = "SWORD";
		switch(Logic.random.nextInt(8)) {
		case 0: case 1: case 2:
			item = "SWORD";
			break;
		case 3:
			item = "PRECIOUS ORB";
			break;
		case 4: case 5:
			item = "BOW";
			break;
		case 6: case 7:
			item = "SHIELD";
			break;
		default:
			item = "TRASH";
			break;
		}
		String txt = "You found \"" + item + "\"!";
		if(!item.equals("TRASH")) {
			if(actor instanceof ActorLiving) {
				ActorLiving luckyActor = (ActorLiving)actor;
				luckyActor.setAttackValue(luckyActor.getAttackValue() + 1);
				Splash dmgUp = new Splash(position.getX(), position.getY() - 1, "DMG +1");
				dmgUp.setTextColor(Color.GREEN);
				dmgUp.show();
			}
		}
		Window w = new Window(map.getWidth() / 2 - (StringHelper.getWidth(txt) / Reference.TILE_SIZE / 2), map.getHeight() / 2, new String[] {txt});
		w.setLifetime(120);
		w.show();
		return null;
	}
	
}
