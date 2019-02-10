package apace.drawing;

public class PaletteSwap {

	private int[] swaps;
	
	public static PaletteSwap WHITE = new PaletteSwap(Palette.WHITE);
	
	public PaletteSwap() {
		this.swaps = new int[Palette.size];
	}
	
	public PaletteSwap(int index) {
		this.swaps = new int[Palette.size];
		for(int i = 0; i < Palette.size; i++) {
			this.swaps[i] = index;
		}
	}
	
	public PaletteSwap(int[] swaps) {
		this.swaps = swaps;
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
