package apace;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
	public static final int SLEEP_TIME = 1000 / UPDATE_RATE;
	
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
					long now = System.currentTimeMillis();
					Game.TIME++;
					
				    mouseHandler.poll();
				    keyHandler.poll();
				    
				    Logic.update(tickCount);
					repaint();
					
					tickCount++;
					
					long delta = System.currentTimeMillis() - now;
					try {
						long sleep = SLEEP_TIME - delta;
						if(sleep > 0) {
							Thread.sleep(sleep);
						}
					} catch (InterruptedException ex) {
					}
				}
			}
		};
		gameThread.start();
	}

	public static BufferedImage buffer = new BufferedImage(WIDTH / Reference.SCALING, HEIGHT / Reference.SCALING, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D gBuffer = buffer.createGraphics();
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		gBuffer.setFont(font);
		gBuffer.setColor(Color.BLACK);
		gBuffer.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		Render.render(gBuffer);
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform scaleTransform = new AffineTransform();
		
		scaleTransform.scale(Reference.SCALING, Reference.SCALING);
		scaleTransform.translate(0.5, 0.5);
		g2.setTransform(scaleTransform);
		g2.drawImage(buffer, 0, 0, null);
		//Render.render(g2);
		
		//scaleTransform.setToIdentity();
		//scaleTransform.scale(Reference.SCALING, Reference.SCALING);
		//g2.setTransform(scaleTransform);
		Render.renderWindows(g2);
	}

	public static void main(String[] args) {
		Sounds.initialize();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(Reference.GAME_TITLE + " " + Reference.GAME_VERSION);
				//frame.setLocation(100, 100);
				frame.setLocationByPlatform(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel panel = new SimpleRoguelike();
				frame.setContentPane(panel);
				//frame.setUndecorated(true);
				frame.setResizable(false);
				frame.pack();
				frame.setVisible(true);
				panel.addKeyListener(keyHandler);
                panel.addMouseListener(mouseHandler);
                panel.addMouseMotionListener(mouseHandler);
                panel.grabFocus();
			}
		});
	}
}
