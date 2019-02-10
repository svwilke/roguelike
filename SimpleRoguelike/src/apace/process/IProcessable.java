package apace.process;

public interface IProcessable {

	default public void start() {
		
	}
	
	default public void enter() {
		
	}
	
	public void update();
	public boolean isDone();
	
	default public void exit() {
		
	}
	
	default public void end() {
		
	}
}
