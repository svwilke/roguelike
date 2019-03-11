package apace.utils;

public class Flags {
	
	public static final int LEFT = 7;
	public static final int RIGHT = 6;
	public static final int UP = 5;
	public static final int DOWN = 4;
	public static final int UP_LEFT = 3;
	public static final int UP_RIGHT = 2;
	public static final int DOWN_LEFT = 1;
	public static final int DOWN_RIGHT = 0;

	public static boolean get(int flags, int pos) {
		int b = 1 << pos;
		return (flags & b) == b;
	}
	
	public static int set(int flags, int pos) {
		return flags | (1 << pos);
	}
	
	public static boolean comp(int flag0, int flag1, int mask) {
		int f1mask = flag1 | mask;
		return ((flag0 | mask) & f1mask) == f1mask;
	}
	
	public static boolean anycomp(int flag0, int[] flags) {
		for(int i = 0; i < flags.length; i++) {
			if(flag0 == flags[i]) {
				return true;
			}
		}
		return false;
	}
	public static boolean anycomp(int flag0, int[] flags, int[] masks) {
		for(int i = 0; i < flags.length; i++) {
			if((flag0 | masks[i]) == (flags[i] | masks[i])) {
				return true;
			}
		}
		return false;
	}
}
