package apace.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
		try {
			BufferedImage image = ImageIO.read(Sprites.class.getResource("/res/images/" + fileName));
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
			e.printStackTrace();
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
}
