package apace.gameplay.map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apace.core.Game;
import apace.core.Logic;
import apace.gameplay.IInteractable;
import apace.gameplay.actor.Actor;
import apace.gameplay.actor.ActorEnemy;
import apace.gameplay.actor.ActorPlayer;
import apace.process.IProcessable;
import apace.process.MultiProcess;
import apace.process.ScheduledCall;
import apace.process.SequenceProcess;
import apace.utils.Position;

public class Map {
	private int width;
	private int height;
	private Tile[][] tiles;
	//private HashMap<Position, Tile> tiles;
	private HashMap<Position, Actor> actors;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		clear();
	}
	
	public void clear() {
		tiles = new Tile[width][height];
		actors = new HashMap<Position, Actor>();
	}
	
	public void generate(int x, int y) {
		clear();
		System.out.println("Creating map (" + width + ", " + height + ").");
		new RandomWallGenerator().generate(this, x, y, 0);
		//new RoomGenerator().generate(this, x, y, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setTile(Position p, Tile t) {
		if(isInBounds(p)) {
			tiles[p.getX()][p.getY()] = t;
		}
	}
	
	public Tile getTile(Position p) {
		if(!isInBounds(p)) {
			throw new IllegalArgumentException("Position argument is out of bounds.");
		}
		return tiles[p.getX()][p.getY()];
	}
	
	public Actor getActor(Position p) {
		if(hasActor(p)) {
			return actors.get(p);
		}
		return null;
	}
	
	public boolean hasActor(Position p) {
		return actors.containsKey(p);
	}
	
	public void moveActor(Position oldPos, Position newPos) {
		if(hasActor(oldPos) && !hasActor(newPos)) {
			Actor actor = getActor(oldPos);
			if(hasTile(oldPos)) {
				getTile(oldPos).onTileExit(this, oldPos, actor);
			}
			actors.put(newPos, actors.get(oldPos));
			actors.remove(oldPos);
			if(hasTile(newPos)) {
				getTile(newPos).onTileEnter(this, newPos, actor);
			}
		}
	}
	
	public void addActor(Position p, Actor a) {
		if(hasActor(p)) {
			throw new IllegalArgumentException("Map already contained an actor at position " + p.toString() + ".");
		}
		actors.put(p, a);
		a.setPosition(p);
	}
	
	public void removeActor(Position p, Actor a) {
		if(hasActor(p)) {
			actors.remove(p, a);
		}
	}
	
	public boolean hasTile(Position p) {
		return isInBounds(p) && tiles != null && tiles[p.getX()][p.getY()] != null;
	}
	
	public boolean isInBounds(Position p) {
		return p.getX() >= 0 && p.getX() < width && p.getY() >= 0 && p.getY() < height;
	}
	
	public boolean isFree(Position p) {
		return !hasTile(p) || this.isWalkable(p);
	}
	
	public boolean isWalkable(Position p) {
		if(actors.containsKey(p)) {
			return actors.get(p).isWalkable();
		}
		if(hasTile(p)) {
			return getTile(p).isWalkable();
		}
		return false;
	}
	
	public boolean isInteractable(Position p) {
		if(actors.containsKey(p)) {
			return actors.get(p) instanceof IInteractable;
		}
		if(hasTile(p)) {
			return getTile(p) instanceof IInteractable;
		}
		return false;
	}
	
	public IInteractable getInteractable(Position p) {
		if(actors.containsKey(p)) {
			return (IInteractable)actors.get(p);
		}
		if(hasTile(p)) {
			return (IInteractable)getTile(p);
		}
		return null;
	}
	
	public void render(Graphics2D g, int offsetX, int offsetY) {

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Position p = new Position(x, y);
				if(hasTile(p)) {
					getTile(p).render(g, this, Game.palette, p);
				}
			}
		}
		List<Actor> delayedDraws = new ArrayList<Actor>(10);
		actors.forEach((pos, actor) -> {
			if(!(actor instanceof ActorPlayer)) {
				actor.render(g, this, Game.palette, pos);
			} else {
				delayedDraws.add(actor);
			}
		}); 
		delayedDraws.forEach((actor) -> actor.render(g, this, Game.palette, actor.getPosition()));
		// ^ actor.getPosition instead of map key position might cause problems
	}
	
}
