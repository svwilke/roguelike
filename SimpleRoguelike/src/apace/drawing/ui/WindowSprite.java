package apace.drawing.ui;

import java.awt.Graphics2D;

import apace.core.Game;
import apace.core.Render;
import apace.drawing.Sprite;
import apace.drawing.Window;

public class WindowSprite extends Window {

	private Sprite sprite;
	
	public WindowSprite(Sprite sprite, int tx, int ty) {
		super(tx, ty, sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(Graphics2D g) {
		if(lifetime < Integer.MAX_VALUE) {
			lifetime--;
		}
		int x = px;
		int y = py;
		sprite.render(g, Game.palette, x, y);
	}
	
	public void close() {
		Render.removeWindow(this);
	}
}
