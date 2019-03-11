package apace.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import apace.drawing.Palette;
import apace.drawing.Sprite;
import apace.lib.Reference;
import apace.lib.Sprites;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private HashMap<Integer, Integer> colorIndices;

	public SpriteSheet(String fileName, Palette palette) {
		URL url = Sprites.class.getResource("/images/" + fileName);
		try {
			BufferedImage image = ImageIO.read(url);
			int w = image.getWidth();
			int h = image.getHeight();
			
			sheet = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = sheet.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			
			colorIndices = new HashMap<>();
			for(int i = 0; i < Palette.size; i++) {
				Color c = palette.getColor(i);
				if(c == null) {
					break;
				}
				colorIndices.put(c.getRGB(), i);
			}
		} catch (IOException e) {
			System.err.println("Image not found: " + url.toString());
			//e.printStackTrace();
		}
	}
	
	public Sprite getSprite(int x, int y) {
		return getSprite(x, y, Reference.TILE_SIZE, Reference.TILE_SIZE);
	}
	
	private HashMap<Position, Sprite> sprites = new HashMap<>();
	
	public Sprite getSprite(int x, int y, int w, int h) {
		Position pos = new Position(x, y);
		if(sprites.containsKey(pos)) {
			return sprites.get(pos);
		}
		x *= Reference.TILE_SIZE;
		y *= Reference.TILE_SIZE;
		int[] data = new int[w * h];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				int v = sheet.getRGB(x + i, y + j);
				if(colorIndices.containsKey(v)) {
					data[i + (j * w)] = colorIndices.get(v);
				} else {
					data[i + (j * w)] = 0;
				}
			}
			
		}
		Sprite sprite = new Sprite(data, w, h);
		sprites.put(pos, sprite);
		return sprite;
	}
	
	public Sprite[] createAutotile() {
		Sprite[] tiles = new Sprite[256];
		tiles = new Sprite[256];
		// Cardinals
		for(int i = 128; i <= 143; i++)
			tiles[i] = this.getSprite(2, 0);
		for(int i = 16; i <= 31; i++)
			tiles[i] = this.getSprite(1, 0);
		for(int i = 32; i <= 47; i++)
			tiles[i] = this.getSprite(3, 0);
		for(int i = 64; i <= 79; i++)
			tiles[i] = this.getSprite(0, 0);
		
		for(int i = 192; i <= 207; i++)
			tiles[i] = this.getSprite(0, 3);
		for(int i = 48; i <= 63; i++)
			tiles[i] = this.getSprite(0, 4);
		
		// Outer Corners
		for(int i = 160; i <= 175; i++)
			tiles[i] = Flags.get(i, 3) ? this.getSprite(1, 2) : this.getSprite(5, 2);
		for(int i = 96; i <= 111; i++)
			tiles[i] = Flags.get(i, 2) ? this.getSprite(0, 2) : this.getSprite(4, 2);		
		for(int i = 80; i <= 95; i++)
			tiles[i] = Flags.get(i, 0) ? this.getSprite(0, 1) : this.getSprite(4, 1);		
		for(int i = 144; i <= 159; i++)
			tiles[i] = Flags.get(i, 1) ? this.getSprite(1, 1) : this.getSprite(5, 1);
			
		// Ups
		for(int i = 224; i <= 227; i++)
			tiles[i] = this.getSprite(7, 0);
		for(int i = 236; i <= 239; i++)
			tiles[i] = this.getSprite(1, 3);
		for(int i = 232; i <= 235; i++)
			tiles[i] = this.getSprite(4, 3);
		for(int i = 228; i <= 231; i++)
			tiles[i] = this.getSprite(5, 3);
		
		// Rights
		for(int i = 112; i <= 127; i++) {
			if(Flags.get(i, 2) && Flags.get(i, 0)) {
				tiles[i] = this.getSprite(3, 3);
			} else
			if(Flags.get(i, 2)) {
				tiles[i] = this.getSprite(7, 3);
			} else
			if(Flags.get(i, 0)) {
				tiles[i] = this.getSprite(7, 4);
			} else {
				tiles[i] = this.getSprite(7, 2);
			}
		}
		
		for(int i = 208; i <= 223; i++) {
			if(Flags.get(i, 1) && Flags.get(i, 0)) {
				tiles[i] = this.getSprite(1, 4);
			} else
			if(Flags.get(i, 1)) {
				tiles[i] = this.getSprite(4, 4);
			} else
			if(Flags.get(i, 0)) {
				tiles[i] = this.getSprite(5, 4);
			} else {
				tiles[i] = this.getSprite(7, 1);
			}
		}
		
		for(int i = 176; i <= 191; i++) {
			if(Flags.get(i, 3) && Flags.get(i, 1)) {
				tiles[i] = this.getSprite(2, 3);
			} else
			if(Flags.get(i, 3)) {
				tiles[i] = this.getSprite(6, 3);
			} else
			if(Flags.get(i, 1)) {
				tiles[i] = this.getSprite(6, 4);
			} else {
				tiles[i] = this.getSprite(6, 2);
			}
		}
		
		tiles[240] = getSprite(6, 1);
		tiles[241] = getSprite(3, 5);
		tiles[242] = getSprite(0, 5);
		tiles[243] = getSprite(5, 5);
		tiles[244] = getSprite(2, 5);
		tiles[245] = getSprite(4, 5);
		tiles[246] = getSprite(2, 4);
		tiles[247] = getSprite(3, 2);
		tiles[248] = getSprite(1, 5);
		tiles[249] = getSprite(3, 4);
		tiles[250] = getSprite(6, 5);
		tiles[251] = getSprite(2, 2);
		tiles[252] = getSprite(7, 5);
		tiles[253] = getSprite(3, 1);
		tiles[254] = getSprite(2, 1);
		tiles[255] = getSprite(6, 0);

		for(int i = 0; i <= 15; i++) {
			tiles[i] = this.getSprite(4, 0);
		}
		return tiles;
	}
}
