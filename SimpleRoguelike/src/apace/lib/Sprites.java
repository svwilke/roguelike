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
import apace.utils.SpriteSheet;

public class Sprites {
	
	public static Sprite EMPTY = new Sprite(8, 8);
	public static Sprite WALL;
	public static Sprite[] HOLE;
	public static Sprite HALFWALL;
	public static Sprite FLOOR;
	public static Sprite PLAYER_0;
	public static Sprite PLAYER_1;
	public static Sprite PLAYER_2;
	public static AnimatedSprite PLAYER;
	public static AnimatedSprite SLIME_SMALL;
	public static AnimatedSprite SLIME_LARGE;
	public static AnimatedSprite GROO;
	public static Sprite DOOR;
	public static Sprite CHEST_CLOSED_LARGE;
	public static Sprite CHEST_OPEN_LARGE;
	public static Sprite CHEST_CLOSED_SMALL;
	public static Sprite CHEST_OPEN_SMALL;
	public static Sprite STAIRS_DOWN;
	public static Sprite STAIRS_UP;
	public static Sprite VASE_SMALL;
	public static Sprite BOMB_0;
	public static Sprite BOMB_1;
	public static Sprite BOMB_2;
	public static Sprite BOMB_3;
	public static AnimatedSprite EXPLOSION;
	public static Sprite[] WALLS;
	
	private static SpriteSheet sheet = new SpriteSheet("sprites2.png", Game.palette);
	private static SpriteSheet actorSheet = new SpriteSheet("actors.png", Game.palette);
	private static SpriteSheet wallSheet = new SpriteSheet("walls4.png", Game.palette);
	private static SpriteSheet holeSheet = new SpriteSheet("hole.png", Game.palette);
	
	static {
		WALL = sheet.getSprite(4, 0);
		HALFWALL = wallSheet.getSprite(5, 0, 8, 4);
		FLOOR = sheet.getSprite(5, 0);
		PLAYER = new AnimatedSprite(actorSheet, 0, 0);
		SLIME_SMALL = new AnimatedSprite(actorSheet, 0, 2);
		SLIME_LARGE = new AnimatedSprite(actorSheet, 0, 1);
		GROO = new AnimatedSprite(actorSheet, 0, 3);
		DOOR = sheet.getSprite(6, 0);
		CHEST_CLOSED_LARGE = sheet.getSprite(6, 4);
		CHEST_OPEN_LARGE = sheet.getSprite(7, 4);
		CHEST_CLOSED_SMALL = sheet.getSprite(4, 4);
		CHEST_OPEN_SMALL = sheet.getSprite(5, 4);
		STAIRS_DOWN = sheet.getSprite(2, 0);
		STAIRS_UP = sheet.getSprite(3, 0);
		VASE_SMALL = sheet.getSprite(5, 1);
		WALLS = wallSheet.createAutotile();
		BOMB_0 = new AnimatedSprite(sheet.getSprite(0, 3), sheet.getSprite(4, 3));
		BOMB_1 = new AnimatedSprite(sheet.getSprite(1, 3), sheet.getSprite(5, 3));
		BOMB_2 = new AnimatedSprite(sheet.getSprite(2, 3), sheet.getSprite(6, 3));
		BOMB_3 = new AnimatedSprite(sheet.getSprite(3, 3), sheet.getSprite(7, 3));
		EXPLOSION = new AnimatedSprite(sheet, 0, 4);
		HOLE = holeSheet.createAutotile();
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
