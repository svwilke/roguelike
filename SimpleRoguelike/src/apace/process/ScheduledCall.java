package apace.process;

public class ScheduledCall implements IProcessable {

	private Runnable runnable;
	private boolean oneShot = true;
	private boolean hasCalled;
	
	public ScheduledCall(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public void setOneShot(boolean oneShot) {
		this.oneShot = oneShot;
	}
	
	@Override
	public void enter() {
		if(!oneShot)
			hasCalled = false;
	}
	
	@Override
	public void update() {
		runnable.run();
		hasCalled = true;
	}

	@Override
	public boolean isDone() {
		return hasCalled;
	}

}
