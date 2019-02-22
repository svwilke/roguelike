package apace.gameplay.map;

import apace.drawing.Sprite;
import apace.gameplay.ITurnTaker;
import apace.process.IProcessable;
import apace.utils.Position;

public class TileProcess extends Tile implements ITurnTaker {

	private TileProcess next;
	private ITurnTaker turn;
	
	public TileProcess(boolean walkable, boolean opaque, ITurnTaker action, Sprite... sprites) {
		super(sprites[0], false, false);
		if(sprites.length == 1) {
			turn = action;
		} else {
			Sprite[] nextSprites = new Sprite[sprites.length - 1];
			for(int i = 1; i < sprites.length; i++) {
				nextSprites[i - 1] = sprites[i];
			}
			next = new TileProcess(walkable, opaque, action, nextSprites);
		}
		
	}

	@Override
	public IProcessable takeTurn(Map map, Position position) {
		if(turn != null) {
			return turn.takeTurn(map, position);
		} else
		if(next != null) {
			map.setTile(position, next);
		}
		return null;
	}

	@Override
	public int getPriority() {
		return 1;
	}
	
}
