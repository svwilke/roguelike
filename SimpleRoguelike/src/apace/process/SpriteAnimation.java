package apace.process;

import apace.drawing.AnimatedSprite;
import apace.drawing.ui.WindowSprite;

public class SpriteAnimation implements IProcessable {

	private int duration = 0;
	private int animationTime = 0;

	private AnimatedSprite animation;
	private WindowSprite spriteWindow;
	
	private int tx, ty;
	
	public SpriteAnimation(AnimatedSprite animation, int duration, int tx, int ty) {
		this.duration = duration;
		this.animation = animation;
		this.tx = tx;
		this.ty = ty;
	}
	
	@Override
	public void enter() {
		animationTime = 0;
		spriteWindow = new WindowSprite(animation.getFrame(0), tx, ty);
		spriteWindow.show();
	}
	
	@Override
	public void update() {
		animationTime++;
		float progress = (float)animationTime / (float)duration;
		spriteWindow.setSprite(animation.getFrame(progress));
	}
	
	@Override
	public boolean isDone() {
		return animationTime >= duration;
	}
	
	@Override
	public void exit() {
		spriteWindow.close();
	}
}
