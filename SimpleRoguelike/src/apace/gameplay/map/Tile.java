package apace.gameplay.map;

import java.awt.Graphics2D;
import java.lang.reflect.Field;

import apace.drawing.Palette;
import apace.drawing.PaletteSwap;
import apace.drawing.Sprite;
import apace.gameplay.ITurnTaker;
import apace.gameplay.actor.Actor;
import apace.lib.Sprites;
import apace.process.IProcessable;
import apace.utils.Position;
import apace.utils.Property;

public class Tile {

	protected Sprite sprite = Sprites.EMPTY;
	protected boolean flipX = false;
	protected boolean flipY = false;
	
	protected boolean isWalkable = true;
	protected boolean isOpaque = false;
	
	protected boolean clearBackground = true;
	
	protected PaletteSwap paletteSwap = null;
	
	public Tile(Sprite sprite, boolean walkable, boolean opaque) {
		this.sprite = sprite;
		this.isWalkable = walkable;
		//this.clearBackground = !walkable;
		this.isOpaque = opaque;
	}
	
	public Tile(Sprite sprite, boolean walkable, boolean opaque, boolean clearBackground) {
		this.sprite = sprite;
		this.isWalkable = walkable;
		//this.clearBackground = !walkable;
		this.isOpaque = opaque;
		this.setClear(clearBackground);
	}
	
	public void setClear(boolean clear) {
		this.clearBackground = clear;
	}
	
	public void setPaletteSwap(PaletteSwap swap) {
		this.paletteSwap = swap;
	}
	
	public PaletteSwap getPaletteSwap() {
		return paletteSwap;
	}
	
	public boolean isWalkable() {
		return this.isWalkable;
	}
	
	public boolean isOpaque() {
		return this.isOpaque;
	}
	
	public void onTileEnter(Map map, Position position, Actor actor) {
		
	}
	
	public void onTileExit(Map map, Position position, Actor actor) {
		
	}
	
	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public void render(Graphics2D g, Map map, Palette p, Position pos) {
		/*if(clearBackground)
			p.swap(0, 1);
		sprite.render(g, p, pos.getPixelX(), pos.getPixelY(), flipX, flipY);
		if(clearBackground)
			p.swap(0, 0);*/
		this.render(g, p, sprite, pos);
	}
	
	protected void render(Graphics2D g, Palette p, Sprite sprite, Position pos) {
		if(paletteSwap != null)
			p.swap(paletteSwap);
		if(clearBackground)
			p.swap(Palette.CLEAR, Palette.BLACK);
		sprite.render(g, p, pos.getPixelX(), pos.getPixelY(), flipX, flipY);
		p.reset();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Property<T> getProperty(String address) {
		Class c = this.getClass();
		while(c != Object.class) {
			try {
				Field f = c.getDeclaredField(address);
				if(f != null) {
					f.setAccessible(true);
				}
				return (Property<T>)f.get(this);
			} catch (NoSuchFieldException e) {
				c = c.getSuperclass();
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | ClassCastException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
