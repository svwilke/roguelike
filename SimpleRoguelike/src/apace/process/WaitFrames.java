package apace.process;

public class WaitFrames implements IProcessable {

	private int framesNeeded = 0;
	private int framesWaited = 0;
	
	public WaitFrames(int frames) {
		framesNeeded = frames;
	}
	
	@Override
	public void enter() {
		framesWaited = 0;
	}
	
	@Override
	public void update() {
		framesWaited++;
	}

	@Override
	public boolean isDone() {
		return framesWaited >= framesNeeded;
	}

}
