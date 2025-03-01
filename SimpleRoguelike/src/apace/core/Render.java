package apace.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import apace.SimpleRoguelike;
import apace.drawing.Sprite;
import apace.drawing.Window;
import apace.lib.Reference;
import apace.utils.Direction;

public class Render {
    
	//private static Sprite test = new Sprite(new byte[][] {{0, 0, 1, 1, 0, 0}, {0, 1, 1, 1, 0, 0}, {0, 0, 1, 1, 1, 0}});
	private static LinkedList<Window> windows = new LinkedList<>();
	private static LinkedList<Window> windowsToRemove = new LinkedList<>();
	
    public static void render(Graphics2D g) {
        if(Game.shouldRender) {
        	/*Game.palette.swap(1, 2);
            test.render(g, Game.palette, 100, 100);
            g.setColor(Game.palette.getColor(1));
            g.drawLine(5, 5, 100, 200);
            Game.palette.reset();*/
        	Game.map.render(g, 0, 0);
        }
        drawText(g, Logic.getStackDescription(), 0, Game.map.getHeight() * Reference.TILE_SIZE, Color.CYAN);
    }
    
    public static void renderWindows(Graphics2D g) {
    	try {
    		for(Window w : windows) {
        		w.render(g);
        	}
    	} catch(ConcurrentModificationException e) {
    		System.err.println("Another window was sucked up into the void.");
    	}
    	windows.removeAll(windowsToRemove);
    	windowsToRemove.clear();
    }
    
    public static void addWindow(Window window) {
    	windows.add(window);
    }
    
    public static void removeWindow(Window window) {
    	windowsToRemove.add(window);
    }
    
    public static void drawSprite(Sprite sprite, int x, int y, boolean flipX, boolean flipY) {
    	for(int i = 0; i < sprite.getWidth(); i++) {
    		for(int j = 0; j < sprite.getHeight(); j++) {
    			int ix = flipX ? sprite.getWidth() - 1 - i : i;
    			int iy = flipY ? sprite.getHeight() - 1 - j : j;
    			Color cn = Game.palette.getColor(sprite.getPixel(ix, iy));
    			if(cn.getAlpha() < 255) {
    				float a = (float)cn.getAlpha() / 255f;
    				float ia = 1f - a;
    				Color cp = new Color(SimpleRoguelike.buffer.getRGB(x + i, y + j));
    				cn = new Color(
    						(int)((float)cn.getRed() * a + (float)cp.getRed() * ia),
    						(int)((float)cn.getGreen() * a + (float)cp.getGreen() * ia),
    						(int)((float)cn.getBlue() * a + (float)cp.getBlue() * ia));
    			}
    			if(x + i >= 0 && x + i < SimpleRoguelike.buffer.getWidth() && y + j >= 0 && y + j < SimpleRoguelike.buffer.getHeight())
    				SimpleRoguelike.buffer.setRGB(x + i, y + j, cn.getRGB());
    		}
    	}
    }
    
    public static void drawWindow(Graphics2D g, int x, int y, int w, int h) {
    	drawWindow(g, x, y, w, h, Game.palette.getColor(1), Game.palette.getColor(3));
    }
    
    public static void drawWindow(Graphics2D g, int x, int y, int w, int h, Color bg, Color fg) {
    	int fgc = fg.getRGB();
    	int bgc = bg.getRGB();
    	for(int i = Math.max(0, x); i < Math.min(SimpleRoguelike.buffer.getWidth(), x + w); i++) {
    		for(int j = Math.max(0, y); j < Math.min(SimpleRoguelike.buffer.getHeight(), y + h); j++) {
    			int c = bgc;
    			if((i == x + 1 || i == x + w - 2) && !(j == y || j == y + h - 1)) {
    				c = fgc;
    			}
    			if(!(i == x || i == x + w - 1) && (j == y + 1 || j == y + h - 2)) {
    				c = fgc;
    			}
    			SimpleRoguelike.buffer.setRGB(i, j, c);
    		}
    	}
    }
    
    public static void drawText(Graphics2D g, String text, int x, int y, Color c) {
    	g.setColor(c);
    	for(String s : text.split("\n")) {
    		g.drawString(s, x, y + 6);
    		y += Reference.TILE_SIZE;
    	}
    	//g.drawString(text, x, y + 6);//(Game.map.getHeight() + 1) * Reference.TILE_SIZE);
    }
    
    public static void drawTextOutlined(Graphics2D g, String text, int x, int y, Color c, Color outC) {
    	//g.setColor(outC);
    	//y = y + 6;
    	for(Direction d : Direction.values()) {
    		int nx = x + d.getX();
    		int ny = y + d.getY();
    		//g.drawString(text, nx, ny);
    		drawText(g, text, nx, ny, outC);
    	}
    	//g.setColor(c);
    	//g.drawString(text, x, y);//(Game.map.getHeight() + 1) * Reference.TILE_SIZE);
    	drawText(g, text, x, y, c);
    }
}
