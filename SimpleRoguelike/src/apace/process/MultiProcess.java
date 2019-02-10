package apace.process;

import java.util.LinkedList;

public class MultiProcess implements IProcessable {

	private LinkedList<IProcessable> processes;
	
	public MultiProcess(IProcessable... processes) {
		this.processes = new LinkedList<IProcessable>();
		for(IProcessable process : processes) {
			if(process != null)
				this.processes.add(process);
		}
	}
	
	public MultiProcess(Iterable<IProcessable> processes) {
		this.processes = new LinkedList<IProcessable>();
		for(IProcessable process : processes) {
			if(process != null)
				this.processes.add(process);
		}
	}
	
	@Override
	public void start() {
		for(IProcessable process : processes) {
			process.start();
		}
	}
	
	@Override
	public void enter() {
		for(IProcessable process : processes) {
			process.enter();
		}
	}

	@Override
	public void update() {
		for(IProcessable process : processes) {
			process.update();
		}
	}

	@Override
	public boolean isDone() {
		for(IProcessable process : processes) {
			if(!process.isDone()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void exit() {
		for(IProcessable process : processes) {
			process.exit();
		}
	}
	
	@Override
	public void end() {
		for(IProcessable process : processes) {
			process.end();
		}
	}
}
