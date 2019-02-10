package apace;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import apace.core.Game;
import apace.core.Logic;
import apace.core.Render;
import apace.handler.KeyHandler;
import apace.handler.MouseHandler;
import apace.lib.Reference;
import apace.lib.Sounds;

public class SimpleRoguelike extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = Reference.WIDTH;
    public static final int HEIGHT = Reference.HEIGHT;
	
	public static final int UPDATE_RATE = 60;
	
	private static int tickCount = 0;
	
	private static Font font;
	
	public static KeyHandler keyHandler = new KeyHandler();
	public static MouseHandler mouseHandler = new MouseHandler();
	
	public SimpleRoguelike() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, SimpleRoguelike.class.getResourceAsStream(Reference.FONT_FILE));
			font = font.deriveFont(4f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread gameThread = new Thread() {
			public void run() {
			    Game.startGame();
				while (true) {
					Game.TIME++;
					
				    mouseHandler.poll();
				    keyHandler.poll();
				    
				    Logic.update(tickCount);
					repaint();
					
					tickCount++;
					
					
					try {
						Thread.sleep(1000 / UPDATE_RATE);
					} catch (InterruptedException ex) {
					}
				}
			}
		};
		gameThread.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform scaleTransform = new AffineTransform();
		
		scaleTransform.scale(Reference.SCALING, Reference.SCALING);
		scaleTransform.translate(0.5, 0.5);
		g2.setTransform(scaleTransform);
		Render.render(g2);
		
		scaleTransform.setToIdentity();
		scaleTransform.scale(Reference.SCALING, Reference.SCALING);
		g2.setTransform(scaleTransform);
		Render.renderWindows(g2);
	}

	public static void main(String[] args) {
		Sounds.initialize();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(Reference.GAME_TITLE + " " + Reference.GAME_VERSION);
				//frame.setLocation(-12, 10);
				frame.setLocationByPlatform(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new SimpleRoguelike());
				//frame.setUndecorated(true);
				frame.setResizable(false);
				frame.pack();
				frame.setVisible(true);
				frame.addKeyListener(keyHandler);
                frame.addMouseListener(mouseHandler);
                frame.addMouseMotionListener(mouseHandler);
			}
		});
	}
}
