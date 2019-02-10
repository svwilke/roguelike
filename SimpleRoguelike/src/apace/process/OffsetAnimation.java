package apace.process;
import apace.utils.Position;

public class OffsetAnimation implements IProcessable {

	private float animationTime = 0f;
	private float animationSpeed = 0.2f;
	
	private Position position;
	private int fromX, fromY, deltaX, deltaY;
	
	public OffsetAnimation(Position pos, int fromX, int fromY, int toX, int toY) {
		this.position = pos;
		this.fromX = fromX;
		this.fromY = fromY;
		this.deltaX = toX - fromX;
		this.deltaY = toY - fromY;
	}
	
	public void setPosition(Position newPosition) {
		this.position = newPosition;
	}
	
	public OffsetAnimation setSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
		return this;
	}
	
	@Override
	public void enter() {
		animationTime = 0f;
	}
	
	@Override
	public void update() {
		animationTime = Math.min(animationTime + animationSpeed, 1f);
		position.setOffsetX(fromX + (int)(animationTime * deltaX));
		position.setOffsetY(fromY + (int)(animationTime * deltaY));
	}
	
	@Override
	public boolean isDone() {
		return animationTime >= 1f;
	}

	@Override
	public void end() {
		position.setOffsetX(fromX + deltaX);
		position.setOffsetY(fromY + deltaY);
	}
}
