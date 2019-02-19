package apace.drawing;

public class PaletteSwap {

	private int[] swaps;
	
	public static PaletteSwap WHITE = new PaletteSwap(Palette.WHITE);
	public static PaletteSwap DARK = new PaletteSwap(new int[] {0,0,1,1,2,1,13,6,4,4,9,3,13,1,13,14});
	
	public PaletteSwap() {
		this.swaps = new int[Palette.size];
	}
	
	public PaletteSwap(int index) {
		this.swaps = new int[Palette.size];
		for(int i = 0; i < Palette.size; i++) {
			this.swaps[i] = index;
		}
		this.swaps[Palette.CLEAR] = Palette.CLEAR;
	}
	
	public PaletteSwap(int[] swaps) {
		this();
		for(int i = 0; i < swaps.length; i++) {
			this.swaps[i] = swaps[i];
		}
	}
	
	public void setSwap(int from, int to) {
		if(from >= Palette.size || to >= Palette.size || from < 0 || to < 0) {
			throw new IllegalArgumentException("Swap source or destination were out of bounds of the palette.");
		}
		this.swaps[from] = to;
	}
	
	public void apply(Palette palette) {
		for(int i = 0; i < Palette.size; i++) {
			palette.swap(i, swaps[i]);
		}
	}
}
