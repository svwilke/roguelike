package apace.drawing;

import java.awt.Graphics2D;

import apace.core.Game;
import apace.utils.SpriteSheet;

public class AnimatedSprite extends Sprite {

	private static final int CONSTANT = 15;
	
	private Sprite[] frames;
	
	public AnimatedSprite(Sprite... sprites) {
		super(0, 0);
		frames = sprites;
	}
	
	public AnimatedSprite(SpriteSheet sheet, int x, int y) {
		super(0, 0);
		frames = new Sprite[] {
			sheet.getSprite(x, y),
			sheet.getSprite(x + 1, y),
			sheet.getSprite(x + 2, y),
			sheet.getSprite(x + 3, y)
		};
	}
	
	@Override
	public void render(Graphics2D g, Palette p, int x, int y) {
		Sprite currentFrame = frames[(Game.TIME / CONSTANT) % frames.length];
		currentFrame.render(g, p, x, y);
	}
	
	@Override
	public void render(Graphics2D g, Palette p, int x, int y, boolean flipX, boolean flipY) {
		Sprite currentFrame = frames[(Game.TIME / CONSTANT) % frames.length];
		currentFrame.render(g, p, x, y, flipX, flipY);
	}
}
