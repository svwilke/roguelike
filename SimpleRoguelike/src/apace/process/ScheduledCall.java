package apace.process;

public class ScheduledCall implements IProcessable {

	private Runnable runnable;
	private boolean hasCalled;
	
	public ScheduledCall(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void enter() {
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
