package apace.utils;

public class Flags {

	public static boolean get(int flags, int pos) {
		int b = 1 << pos;
		return (flags & b) == b;
	}
}
