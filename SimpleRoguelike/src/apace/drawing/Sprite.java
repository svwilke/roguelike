package apace.drawing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apace.core.Game;

public class Sprite {
	
	private int width;
	private int height;
	private int[] data;
	
	private BufferedImage bufferedNormalImage;
	private BufferedImage bufferedNormalImageFlipX;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		data = new int[width * height];
	}
	
	public Sprite(int[] data, int width, int height) {
		this.width = width;
		this.height = height;
		this.data = data;
		prerender();
	}
	
	public Sprite(int[][] data) {
		this(data[0].length, data.length);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				this.data[x + (y * width)] = data[y][x];
			}
		}
		prerender();
	}
	
	public void prerender() {
		bufferedNormalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferedNormalImageFlipX = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufferedNormalImage.createGraphics();
		Graphics2D gFlip = bufferedNormalImageFlipX.createGraphics();
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					g.setColor(Game.palette.getColor(data[i + j * width]));
					g.drawLine(i, j, i, j);
					gFlip.setColor(Game.palette.getColor(data[width - 1 - i + j * width]));
					gFlip.drawLine(i, j, i, j);
				}
			}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void add(Sprite other) {
		for(int i = 0; i < data.length && i < other.data.length; i++) {
			if(other.data[i] != 0) {
				data[i] = other.data[i];
			}
		}
	}
	
	public Sprite copy() {
		return new Sprite(data.clone(), width, height);
	}
	
	public void render(Graphics2D g, Palette p, int x, int y) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				g.setColor(p.getColor(data[i + j * width]));
				g.drawLine(x + i, y + j, x + i, y + j);
			}
		}
	}
	
	public void render(Graphics2D g, Palette p, int x, int y, boolean flipX, boolean flipY) {
		g.drawImage(flipX ? bufferedNormalImageFlipX : bufferedNormalImage, x, y, null);
		/*
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int sx = flipX ? (width - 1 - i) : i;
				int sy = flipY ? (height - 1 - j) : j;
				g.setColor(p.getColor(data[sx + sy * width]));
				g.drawLine(x + i, y + j, x + i, y + j);
			}
		}
		*/
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				if(j > 0)
					s += " ";
				int v = data[j + (i * this.width)];
				if(v < 10)
					s += " ";
				s += v;
			}
			if(i < this.height - 1) {
				s += "\n";
			}
		}
		return s;
	}
}
