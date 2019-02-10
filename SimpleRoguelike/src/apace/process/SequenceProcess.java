package apace.process;

import java.util.LinkedList;
import java.util.Queue;

public class SequenceProcess implements IProcessable {

	private IProcessable current = null;
	private Queue<IProcessable> processes;
	
	public SequenceProcess(IProcessable... processes) {
		this.processes = new LinkedList<IProcessable>();
		for(IProcessable process : processes) {
			this.processes.add(process);
		}
	}
	
	public SequenceProcess(Iterable<IProcessable> processes) {
		this.processes = new LinkedList<IProcessable>();
		for(IProcessable process : processes) {
			this.processes.add(process);
		}
	}

	@Override
	public void start() {
		current = processes.poll();
		if(current != null) {
			current.start();
		}
	}
	
	@Override
	public void enter() {
		if(current != null) {
			current.enter();
		}
	}

	@Override
	public void update() {
		if(current == null) {
			return;
		}
		if(current.isDone()) {
			current.exit();
			current.end();
			current = processes.poll();
			if(current != null) {
				current.start();
				current.enter();
			} else {
				return;
			}
		}
		current.update();
	}
	
	@Override
	public void exit() {
		if(current != null) {
			current.exit();
		}
	}
	
	@Override
	public void end() {
		if(current != null) {
			current.end();
		}
	}

	@Override
	public boolean isDone() {
		return current == null;
	}
}
