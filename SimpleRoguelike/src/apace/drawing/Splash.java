package apace.drawing;

import java.awt.Graphics2D;

import apace.core.Game;
import apace.core.Render;
import apace.lib.Reference;
import apace.utils.StringHelper;

public class Splash extends Window {

	private int targetX, targetY;
	
	public Splash(int tx, int ty, String text) {
		this(tx, ty, text, 0, -10);
	}
	
	public Splash(int tx, int ty, String text, int dx, int dy) {
		this(tx, ty, text, dx, dy, 30);
	}
	
	public Splash(int tx, int ty, String text, int dx, int dy, int lifetime) {
		super(tx, ty, 1, 1);
		pw = StringHelper.getWidth(text);
		px = tx * Reference.TILE_SIZE - (pw / 2); //+ (Reference.TILE_SIZE/2);
		targetX = px + dx;
		targetY = py + dy;
		lines = new String[] {text};
		this.lifetime = lifetime;
	}
	
	@Override
	public void render(Graphics2D g) {
		lifetime--;
		px += (targetX - px) / 5;
		py += (targetY - py) / 5;
		if(lifetime <= 0) {
			Render.removeWindow(this);
		}
		for(int i = 0; i < lines.length; i++) {
			Render.drawTextOutlined(g, lines[i], px + Reference.TILE_SIZE / 2, py + (int)(Reference.TILE_SIZE * (i + 0.5f)), textColor, Game.palette.getColor(Palette.BLACK));
		}
	}
}
