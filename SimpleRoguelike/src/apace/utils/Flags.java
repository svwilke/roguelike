package apace.utils;

public class Flags {

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
}
