package apace.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

import apace.core.Game;
import apace.core.Render;
import apace.lib.Reference;
import apace.utils.StringHelper;

public class Window {
	
	protected int px;
	protected int py;
	protected int pw;
	protected int ph;
	protected String[] lines;
	
	protected int lifetime = Integer.MAX_VALUE;
	
	protected Color textColor = Game.palette.getColor(Palette.GRAY);
	
	public Window(int tx, int ty, int tw, int th) {
		px = tx * Reference.TILE_SIZE;
		py = ty * Reference.TILE_SIZE;
		pw = tw * Reference.TILE_SIZE;
		ph = th * Reference.TILE_SIZE;
		lines = new String[0];
	}
	
	public Window(int tx, int ty, int tw, String[] lines) {
		this(tx, ty, tw, lines.length + 1);
		this.lines = lines;
	}
	
	public Window(int tx, int ty, String[] lines) {
		this(tx, ty, 0, lines.length + 1);
		for(String s : lines) {
			pw = Math.max(pw, Reference.TILE_SIZE + StringHelper.getWidth(s));
		}
		this.lines = lines;
	}
	
	public void setTextColor(int paletteIndex) {
		setTextColor(Game.palette.getColor(paletteIndex));
	}
	
	public void setTextColor(Color c) {
		textColor = c;
	}
	
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	
	public void setText(String text) {
		this.lines = text.split("\n");
	}
	
	public void show() {
		Render.addWindow(this);
	}
	
	public void close() {
		lifetime = 0;
	}
	
	public boolean isClosing() {
		return lifetime < 0;
	}
	
	public void render(Graphics2D g) {
		if(lifetime < Integer.MAX_VALUE) {
			lifetime--;
		}
		if(lifetime < 0) {
			int dif = ph / 4;
			ph -= dif;
			py += dif / 2;
			if(ph <= 3) {
				Render.removeWindow(this);
			}
		}
		int x = px;
		int y = py;
		int w = pw;
		int h = ph;
		//y += (int)(3.0 * Math.sin((double)Game.TIME / 20.0));
		Render.drawWindow(g, x, y, w, h, Game.palette.getColor(Palette.BLACK), textColor);
		g.clipRect(x + 3, y + 3, w - 6, h - 6);
		for(int i = 0; i < lines.length; i++) {
			Render.drawText(g, lines[i], x + Reference.TILE_SIZE / 2, y + (int)(Reference.TILE_SIZE * (i + 0.5f)), textColor);
		}
		g.setClip(null);
	}
}
