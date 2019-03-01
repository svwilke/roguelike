package apace.gameplay.map;

import java.awt.Graphics2D;

import apace.drawing.Palette;
import apace.lib.Sprites;
import apace.utils.Direction;
import apace.utils.Position;

public class TileWall extends Tile {

	public TileWall() {
		super(Sprites.WALL, false, true);
	}
	
	@Override
	public void render(Graphics2D g, Map map, Palette p, Position pos) {
		int bitfield = getSurrounding(map, pos);
		if(Sprites.WALLS[bitfield] != null) {
			
			super.render(g, p, Sprites.WALLS[bitfield], pos);
		} else {
			super.render(g, map, p, pos);
		}
		//g.setColor(Color.white);
		//g.drawString("" + bitfield, pos.getPixelX(), pos.getPixelY() + 5);
		//Render.drawText(g, "" + bitfield, pos.getPixelX(), pos.getPixelY(), Color.white);
		/*Position below = pos.down();
		if(map.isWalkable(below)) {
			p.swap(0, 1);
			Sprites.HALFWALL.render(g, p, below.getPixelX(), below.getPixelY(), flipX, flipY);
			p.swap(0, 0);
		}*/
	}
	
	public int getSurrounding(Map map, Position pos) {
		int b = 0;
		int add = 128;
		for(Direction d : Direction.values()) {
			int same = 1;
			if(map.isInBounds(d.from(pos)) && !(map.getTile(d.from(pos)) instanceof TileWall)) {
				same = 0;
			}
			b += same * add;
			add /= 2;
		}
		return b;
	}
}
