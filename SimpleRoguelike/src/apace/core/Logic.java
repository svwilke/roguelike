package apace.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import apace.process.IProcessable;

public class Logic {
    
    public static Random random = new Random();
    
    private static HashSet<IProcessable> startedSet = new HashSet<>();
    
    private static IProcessable lastProcess = null;
    private static Stack<IProcessable> processStack = new Stack<IProcessable>();
    
    private static LinkedList<IProcessable> processQueue = new LinkedList<IProcessable>();
    
    public static void update(int tickCount) {
        if(!processStack.isEmpty()) {
        	IProcessable current = processStack.peek();
        	if(current != lastProcess) {
        		if(!startedSet.contains(current)) {
        			current.start();
        			startedSet.add(current);
        		}
        		current.enter();
        		lastProcess = current;
        	}
        	if(current.isDone()) {
            	pop();
            } else {
            	current.update();
            }
        }
        enqueueProcesses();
    }
    
    private static void enqueueProcesses() {
    	for(IProcessable process : processQueue) {
    		processStack.push(process);
    	}
    	processQueue.clear();
    }
    
    public static void pop() {
    	processStack.peek().exit();
    	processStack.pop().end();
    }
    
    public static void push(IProcessable process) {
    	processQueue.add(process);
    	/*if(!processStack.isEmpty()) {
    		processStack.peek().exit();
    	}
    	processStack.push(process);
    	process.start();
    	*/
    }
    
    public static String getStackDescription() {
    	String list = "";
    	for(IProcessable process : processStack) {
    		list += "\n";
    		list += process.getClass().getSimpleName();
    	}
    	return list;
    }
}
