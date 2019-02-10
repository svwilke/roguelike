package apace.drawing;

import java.awt.Color;

public class Palette {

	public static final int CLEAR = 0;
	public static final int BLACK = 1;
	public static final int DARK_GRAY = 2;
	public static final int LIGHT_GRAY = 3;
	public static final int WHITE = 4;
	public static final int YELLOW = 5;
	public static final int ORANGE = 6;
	
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
