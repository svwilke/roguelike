package apace.process;

import apace.drawing.PaletteSwap;
import apace.gameplay.map.Tile;

public class FlashAnimation implements IProcessable {

	private int duration = 0;
	private int animationTime = 0;
	
	private PaletteSwap flashSwap;
	private PaletteSwap oldSwap;
	
	private Tile tile;
	
	public FlashAnimation(Tile tile, int duration, PaletteSwap swap) {
		this.duration = duration;
		this.flashSwap = swap;
		this.oldSwap = tile.getPaletteSwap();
		this.tile = tile;
	}
	
	@Override
	public void enter() {
		animationTime = 0;
		this.oldSwap = tile.getPaletteSwap();
		tile.setPaletteSwap(flashSwap);
	}
	
	@Override
	public void update() {
		animationTime++;
	}
	
	@Override
	public boolean isDone() {
		return animationTime >= duration;
	}
	
	@Override
	public void exit() {
		tile.setPaletteSwap(oldSwap);
	}
}
