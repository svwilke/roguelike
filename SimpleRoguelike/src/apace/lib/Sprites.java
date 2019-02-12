package apace.lib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import apace.core.Game;
import apace.drawing.AnimatedSprite;
import apace.drawing.Palette;
import apace.drawing.Sprite;
import apace.utils.Flags;
import apace.utils.SpriteSheet;

public class Sprites {
	
	public static Sprite EMPTY = new Sprite(8, 8);
	public static Sprite WALL;
	public static Sprite HALFWALL;
	public static Sprite FLOOR;
	public static Sprite PLAYER_0;
	public static Sprite PLAYER_1;
	public static Sprite PLAYER_2;
	public static Sprite PLAYER;
	public static Sprite SLIME;
	public static Sprite DOOR;
	public static Sprite CHEST_CLOSED_LARGE;
	public static Sprite CHEST_OPEN_LARGE;
	public static Sprite CHEST_CLOSED_SMALL;
	public static Sprite CHEST_OPEN_SMALL;
	public static Sprite STAIRS_DOWN;
	public static Sprite STAIRS_UP;
	public static Sprite VASE_SMALL;
	public static Sprite[] WALLS;
	
	private static SpriteSheet sheet = new SpriteSheet("sprites.png", Game.palette);
	private static SpriteSheet actorSheet = new SpriteSheet("actors.png", Game.palette);
	private static SpriteSheet wallSheet = new SpriteSheet("walls.png", Game.palette);
	
	static {
		WALL = wallSheet.getSprite(5, 0);
		HALFWALL = sheet.getSprite(4, 1, 8, 4);
		FLOOR = sheet.getSprite(5, 0);
		/*PLAYER_0 = sheet.getSprite(0, 4);
		PLAYER_1 = sheet.getSprite(1, 4);
		PLAYER_2 = sheet.getSprite(2, 4);
		PLAYER = new AnimatedSprite(PLAYER_0, PLAYER_1, PLAYER_2, PLAYER_1);*/
		PLAYER = new AnimatedSprite(actorSheet, 0, 0);
		SLIME = new AnimatedSprite(actorSheet, 0, 2);
		DOOR = sheet.getSprite(6, 0);
		CHEST_CLOSED_LARGE = sheet.getSprite(6, 4);
		CHEST_OPEN_LARGE = sheet.getSprite(7, 4);
		CHEST_CLOSED_SMALL = sheet.getSprite(4, 4);
		CHEST_OPEN_SMALL = sheet.getSprite(5, 4);
		STAIRS_DOWN = sheet.getSprite(2, 0);
		STAIRS_UP = sheet.getSprite(3, 0);
		VASE_SMALL = sheet.getSprite(5, 1);
		WALLS = wallSheet.createAutotile();
		/*WALLS = new Sprite[256];
		// Cardinals
		for(int i = 128; i <= 143; i++)
			WALLS[i] = wallSheet.getSprite(2, 0);
		for(int i = 16; i <= 31; i++)
			WALLS[i] = wallSheet.getSprite(1, 0);
		for(int i = 32; i <= 47; i++)
			WALLS[i] = wallSheet.getSprite(3, 0);
		for(int i = 64; i <= 79; i++)
			WALLS[i] = wallSheet.getSprite(0, 0);
		
		for(int i = 192; i <= 207; i++)
			WALLS[i] = wallSheet.getSprite(0, 3);
		for(int i = 48; i <= 63; i++)
			WALLS[i] = wallSheet.getSprite(0, 4);
		
		// Outer Corners
		for(int i = 160; i <= 175; i++)
			WALLS[i] = Flags.get(i, 3) ? wallSheet.getSprite(1, 2) : wallSheet.getSprite(5, 2);
		for(int i = 96; i <= 111; i++)
			WALLS[i] = Flags.get(i, 2) ? wallSheet.getSprite(0, 2) : wallSheet.getSprite(4, 2);		
		for(int i = 80; i <= 95; i++)
			WALLS[i] = Flags.get(i, 0) ? wallSheet.getSprite(0, 1) : wallSheet.getSprite(4, 1);		
		for(int i = 144; i <= 159; i++)
			WALLS[i] = Flags.get(i, 1) ? wallSheet.getSprite(1, 1) : wallSheet.getSprite(5, 1);
			
		// Ups
		for(int i = 224; i <= 227; i++)
			WALLS[i] = wallSheet.getSprite(7, 0);
		for(int i = 236; i <= 239; i++)
			WALLS[i] = wallSheet.getSprite(1, 3);
		for(int i = 232; i <= 235; i++)
			WALLS[i] = wallSheet.getSprite(4, 3);
		for(int i = 228; i <= 231; i++)
			WALLS[i] = wallSheet.getSprite(5, 3);
		
		// Rights
		for(int i = 112; i <= 127; i++) {
			if(Flags.get(i, 2) && Flags.get(i, 0)) {
				WALLS[i] = wallSheet.getSprite(3, 3);
			} else
			if(Flags.get(i, 2)) {
				WALLS[i] = wallSheet.getSprite(7, 3);
			} else
			if(Flags.get(i, 0)) {
				WALLS[i] = wallSheet.getSprite(7, 4);
			} else {
				WALLS[i] = wallSheet.getSprite(7, 2);
			}
		}
		
		for(int i = 208; i <= 223; i++) {
			if(Flags.get(i, 1) && Flags.get(i, 0)) {
				WALLS[i] = wallSheet.getSprite(1, 4);
			} else
			if(Flags.get(i, 1)) {
				WALLS[i] = wallSheet.getSprite(4, 4);
			} else
			if(Flags.get(i, 0)) {
				WALLS[i] = wallSheet.getSprite(5, 4);
			} else {
				WALLS[i] = wallSheet.getSprite(7, 1);
			}
		}
		
		for(int i = 176; i <= 191; i++) {
			if(Flags.get(i, 3) && Flags.get(i, 1)) {
				WALLS[i] = wallSheet.getSprite(2, 3);
			} else
			if(Flags.get(i, 3)) {
				WALLS[i] = wallSheet.getSprite(6, 3);
			} else
			if(Flags.get(i, 1)) {
				WALLS[i] = wallSheet.getSprite(6, 4);
			} else {
				WALLS[i] = wallSheet.getSprite(6, 2);
			}
		}
		
		for(int i = 240; i <= 255; i++) {
			Sprite corner = EMPTY.copy();
			if(!Flags.get(i, 0))
				corner.add(wallSheet.getSprite(2, 1));
			if(!Flags.get(i, 1))
				corner.add(wallSheet.getSprite(3, 1));
			if(!Flags.get(i, 2))
				corner.add(wallSheet.getSprite(2, 2));
			if(!Flags.get(i, 3))
				corner.add(wallSheet.getSprite(3, 2));
			WALLS[i] = corner;
		}
		
		for(int i = 0; i <= 15; i++) {
			WALLS[i] = wallSheet.getSprite(4, 0);
		}*/
	}
	
	public static Sprite loadSprite(String fileName, Palette palette) {
		try {
			BufferedImage image = ImageIO.read(Sprites.class.getResource("/res/images/" + fileName));
			int w = image.getWidth();
			int h = image.getHeight();
			
			BufferedImage imageARGB = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = imageARGB.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			
			HashMap<Integer, Integer> colorIndices = new HashMap<>();
			for(int i = 0; i < Palette.size; i++) {
				Color c = palette.getColor(i);
				if(c == null) {
					break;
				}
				colorIndices.put(c.getRGB(), i);
			}
			int[] data = new int[w * h];
			for(int x = 0; x < w; x++) {
				for(int y = 0; y < h; y++) {
					int v = image.getRGB(x, y);
					if(colorIndices.containsKey(v)) {
						data[x + (y * w)] = colorIndices.get(v);
					} else {
						data[x + (y * w)] = 0;
					}
				}
				
			}
			String s = "";
			for(int i = 0; i < data.length; i++) {
				s += data[i] + " ";
			}
			System.out.println(s);
			Sprite sprite = new Sprite(data, w, h);
			return sprite;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
