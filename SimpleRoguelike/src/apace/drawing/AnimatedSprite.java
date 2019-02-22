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
	
	public Sprite getFrame(int frameIndex) {
		if(frameIndex < 0 || frameIndex >= frames.length) {
			throw new IllegalArgumentException("FrameIndex out of bounds.");
		}
		return frames[frameIndex];
	}
	
	public Sprite getFrame(float animTime) {
		int frame = (int)(animTime * frames.length - 1);
		return getFrame(frame);
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
