package apace.drawing;

import java.awt.Color;

public class Palette {

	public static final int CLEAR = 16;
	public static final int BLACK = 0;
	public static final int DARK_BLUE = 1;
	public static final int DARK_RED = 2;
	public static final int DARK_GREEN = 3;
	public static final int BROWN = 4;
	public static final int DARK_GRAY = 5;
	public static final int GRAY = 6;
	public static final int WHITE = 7;
	public static final int RED = 8;
	public static final int ORANGE = 9;
	public static final int YELLOW = 10;
	public static final int GREEN = 11;
	public static final int BLUE = 12;
	public static final int SLATE = 13;
	public static final int PINK = 14;
	public static final int BEIGE = 15;
	
	public static final int size = 256;
	
	private Color[] colors;
	private int[] swaps;
	
	public Palette() {
		colors = new Color[size];
		swaps = new int[size];
		reset();
	}
	
	public Palette(Color[] colors) {
		this();
		for(int i = 0; i < colors.length && i < size; i++) {
			this.colors[i] = colors[i];
		}
	}
	
	public void reset() {
		for(int i = 0; i < swaps.length; i++) {
			swaps[i] = i;
		}
	}
	
	public void swap(byte index0, byte index1) {
		swaps[index0] = index1;
	}
	
	public void swap(int index0, int index1) {
		swaps[index0] = index1;
	}
	
	public void swap(PaletteSwap swap) {
		swap.apply(this);
	}
	
	public Color getColor(byte index) {
		if(index < size) {
			return colors[swaps[index]];
		}
		throw new IllegalArgumentException("Palette index out of range.");
	}

	public Color getColor(int index) {
		if(index < size) {
			return colors[swaps[index]];
		}
		throw new IllegalArgumentException("Palette index out of range.");
	}
}
