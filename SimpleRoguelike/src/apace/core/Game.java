package apace.core;

import java.awt.Color;

import apace.SimpleRoguelike;
import apace.drawing.Palette;
import apace.gameplay.actor.ActorPlayer;
import apace.gameplay.map.Map;
import apace.handler.KeyHandler;
import apace.handler.Keys;
import apace.handler.MouseHandler;
import apace.lib.Reference;
import apace.process.IProcessable;
import apace.process.ScheduledCall;
import apace.process.SequenceProcess;
import apace.utils.Direction;
import apace.utils.Position;

public class Game implements IProcessable {

	public static int TIME = 0;
	public static Palette palette = new Palette(new Color[] {
			new Color(0, 0, 0, 0),
			new Color(0, 0, 0, 255),
			new Color(80, 80, 80, 255),
			new Color(190, 190, 190, 255),
			new Color(255, 255, 255, 255),
			new Color(255, 255, 0, 255),
			new Color(255, 127, 0, 255)
		});
	
    public static boolean shouldUpdate = false;
    public static boolean shouldRender = false;
    
    public static KeyHandler keyHandler = SimpleRoguelike.keyHandler;
    public static MouseHandler mouseHandler = SimpleRoguelike.mouseHandler;
    
    public static Map map = new Map(24, 16);
    
    public static ActorPlayer player;
    
    private Game() {
    	player = new ActorPlayer();
    	Position pDown = new Position(1, 1).add(Position.random(map.getWidth() - 2, map.getHeight() - 2));
    	map.generate(pDown.getX(), pDown.getY());
    	map.addActor(pDown, player);
    	//map.addActor(pDown.up().up(), new ActorEnemy(Sprites.SLIME));
    }
    
    public static void startGame() {
        Game.TIME = 0;
        shouldRender = true;
        shouldUpdate = true;
        Logic.push(new Game());
    }

    int f = 0;
	@Override
	public void update() {
		IProcessable anim = null;
		int[] keys = new int[] { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
		Direction[] direction = Direction.values();
		for(int i = 0; i < keys.length; i++) {
			if(keyHandler.isKeyDownOnce(keys[i])) {
				anim = player.tryMove(direction[i], map);
			}
		}
		if(keyHandler.isKeyDownOnce(Keys.SPECIAL1)) {
			Reference.SCALING++;
		}
		if(keyHandler.isKeyDownOnce(Keys.SPECIAL2)) {
			Reference.SCALING--;
		}
		if(keyHandler.isKeyDownOnce(Keys.SPACE)) {
			Logic.push(new SequenceProcess(
				new ScheduledCall(() -> System.out.println("1")),
				new ScheduledCall(() -> System.out.println("2")),
				new ScheduledCall(() -> System.out.println("3"))));
		}
		if(anim != null) {
			Logic.push(anim);
		}
	}

	@Override
	public boolean isDone() {
		return false;
	}
}
