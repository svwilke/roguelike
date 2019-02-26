package apace.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import apace.SimpleRoguelike;
import apace.drawing.Palette;
import apace.drawing.Window;
import apace.drawing.ui.WindowActor;
import apace.gameplay.ITurnTaker;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorLiving;
import apace.gameplay.actor.ActorPlayer;
import apace.gameplay.map.Map;
import apace.gameplay.map.Tile;
import apace.handler.KeyHandler;
import apace.handler.Keys;
import apace.handler.MouseHandler;
import apace.lib.Reference;
import apace.lib.Tiles;
import apace.process.IProcessable;
import apace.process.MultiProcess;
import apace.process.ScheduledCall;
import apace.process.SequenceProcess;
import apace.utils.Direction;
import apace.utils.Position;

public class Game implements IProcessable {

	public static int TIME = 0;
	/*public static Palette palette = new Palette(new Color[] {
			new Color(0, 0, 0, 0),
			new Color(0, 0, 0, 255),
			new Color(80, 80, 80, 255),
			new Color(190, 190, 190, 255),
			new Color(255, 255, 255, 255),
			new Color(255, 255, 0, 255),
			new Color(255, 127, 0, 255)
		});*/
	
	public static Palette palette = new Palette(new Color[] {
		new Color(0, 0, 0),
		new Color(29, 43, 83),
		new Color(126, 37, 83),
		new Color(0, 135, 81),
		new Color(171, 82, 54),
		new Color(95, 87, 79),
		new Color(194, 195, 199),
		new Color(255, 241, 232),
		new Color(255, 0, 77),
		new Color(255, 163, 0),
		new Color(255, 236, 39),
		new Color(0, 228, 54),
		new Color(41, 173, 255),
		new Color(131, 118, 156),
		new Color(255, 119, 168),
		new Color(255, 204, 170),
		new Color(0, 0, 0, 0),
	});
    public static boolean shouldUpdate = false;
    public static boolean shouldRender = false;
    
    public static KeyHandler keyHandler = SimpleRoguelike.keyHandler;
    public static MouseHandler mouseHandler = SimpleRoguelike.mouseHandler;
    
    public static Map map = new Map(24, 16);
    
    public static ActorPlayer player;
    
    private Game() {
    	Position pDown = new Position(1, 1).add(Position.random(map.getWidth() - 2, map.getHeight() - 2));
    	map.generate(pDown.getX(), pDown.getY());
    	map.addActor(pDown, player);
    	//map.updateVisibility();
    	//map.addActor(pDown.up().up(), new ActorEnemy(Sprites.SLIME));
    }
    
    private Window currentWindow;
    private Actor currentActor;
    
    public static void startGame() {
        Game.TIME = 0;
        shouldRender = true;
        shouldUpdate = true;
        player = new ActorPlayer();
        Window ui = new WindowActor(Game.map.getWidth(), 1, player);
		ui.show();
        Logic.push(new Game());
    }

    int f = 0;
	@Override
	public void update() {
		IProcessable anim = null;
		int[] keys = new int[] { Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN };
		Direction[] direction = Direction.values();
		//int key = keyHandler.getBuffer();
		for(int i = 0; i < keys.length; i++) {
			if(keyHandler.isKeyDownOnce(keys[i])) {
			//if(key == keys[i]) {
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
			//Logic.push(new ScheduledCall(() -> Logic.push(doAi())));
			anim = new ScheduledCall(() -> map.setTile(player.getPosition(), Tiles.BOMB));
		}
		if(anim != null) {
			Logic.push(new ScheduledCall(() -> { Logic.push(doAi(0)); }));
			Logic.push(anim);
		}
		
		//if(mouseHandler.isButtonClickedOnce(MouseHandler.PRIMARY)) {
			int px = mouseHandler.mouseX / Reference.SCALING;
			int py = mouseHandler.mouseY / Reference.SCALING;
			int tx = px / (Reference.TILE_SIZE);
			int ty = py / (Reference.TILE_SIZE);
			boolean hasWindow = false;
			if(tx < map.getWidth() && ty < map.getHeight()) {
				Position tMouse = new Position(tx, ty);
				if(map.hasActor(tMouse)) {
					Actor a = map.getActor(tMouse);
					if(a instanceof ActorLiving) {
						if(a != currentActor) {
							if(currentWindow != null) {
								currentWindow.close();
							}
							currentWindow = new WindowActor(Game.map.getWidth(), 4, (ActorLiving)a);
							currentWindow.setPosition(px + 1, py - Reference.TILE_SIZE * 3);
							currentWindow.show();
							hasWindow = true;
							currentActor = a;
						} else {
							currentWindow.setPosition(px + 1, py - Reference.TILE_SIZE * 3);
							hasWindow = true;
						}
						
					}
				}
			}
			if(currentWindow != null && !hasWindow) {
				currentWindow.close();
				currentActor = null;
			}
		//}
	}
	private static final int maxPriority = 10;
	public IProcessable doAi(int priority) {
		map.updateVisibility();
		List<IProcessable> aiTurns = new ArrayList<IProcessable>(map.getActorCount());
		LinkedList<Actor> actors = Game.map.getActors();
		HashMap<Position, Tile> tiles = Game.map.getTiles();
		tiles.forEach((position, tile) -> {
			if(tile instanceof ITurnTaker) {
				if(((ITurnTaker) tile).getPriority() == priority) {
					IProcessable turn = ((ITurnTaker)tile).takeTurn(map, position);
					aiTurns.add(turn);
				}
			}
		});
		actors.forEach((actor) -> {
			if(actor instanceof ITurnTaker) {
				if(((ITurnTaker) actor).getPriority() == priority) {
					IProcessable turn = ((ITurnTaker)actor).takeTurn(Game.map, actor.getPosition());
					aiTurns.add(turn);
				}
			}
		});
		IProcessable aiTurn = new MultiProcess(aiTurns);
		if(priority < maxPriority) {
			aiTurn = new SequenceProcess(aiTurn, new ScheduledCall(() -> doAi(priority + 1)));
		}
		return aiTurn;
	}

	@Override
	public boolean isDone() {
		return false;
	}
}
