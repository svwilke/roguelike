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
    			SimpleRoguelike.buffer.setRGB(x + i, y + j, Game.palette.getColor(sprite.getPixel(ix, iy)).getRGB());
    		}
    	}
    }
    
    public static void drawWindow(Graphics2D g, int x, int y, int w, int h) {
    	drawWindow(g, x, y, w, h, Game.palette.getColor(1), Game.palette.getColor(3));
    }
    
    public static void drawWindow(Graphics2D g, int x, int y, int w, int h, Color bg, Color fg) {
    	g.setColor(bg);
    	g.fillRect(x, y, w, h);
    	g.setColor(fg);
    	g.drawRect(x + 1, y + 1, w - 2, h - 2);
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
