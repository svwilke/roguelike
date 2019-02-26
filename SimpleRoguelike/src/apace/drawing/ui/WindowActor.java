package apace.drawing.ui;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import apace.core.Game;
import apace.drawing.Palette;
import apace.drawing.Sprite;
import apace.drawing.Window;
import apace.gameplay.actor.ActorLiving;
import apace.lib.Reference;
import apace.utils.Property;

public class WindowActor extends Window implements Observer {

	public WindowActor(int tx, int ty, ActorLiving actor) {
		super(tx, ty, new String[] {"   LongNameHere", "   HP and ATK"});
		setActor(actor);
		this.setTextColor(Palette.SLATE);
	}
	
	private String name;
	private Sprite sprite;
	private Property<Integer> maxHealth;
	private Property<Integer> health;
	private Property<Integer> attackValue;
	
	public void setActor(ActorLiving actor) {
		if(maxHealth != null) {
			maxHealth.deleteObserver(this);
		}
		maxHealth = actor.getProperty("maxHealth");
		maxHealth.addObserver(this);
		if(health != null) {
			health.deleteObserver(this);
		}
		health = actor.getProperty("health");
		health.addObserver(this);
		if(attackValue != null) {
			attackValue.deleteObserver(this);
		}
		attackValue = actor.getProperty("attackValue");
		attackValue.addObserver(this);
		sprite = actor.getSprite();
		name = actor.getDisplayName();
		update(null, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(health != null && maxHealth != null && name != null) {
			if(health.getValue() > 0) {
				this.setText(String.format("   %s\n« %d/%d ATK %d", name, health.getValue(), maxHealth.getValue(), attackValue.getValue()));
			} else {
				this.setText(String.format("   %s\nDEAD!", name));
			}
			
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		if(!isClosing()) {
			sprite.render(g, Game.palette, this.px + Reference.TILE_SIZE / 2, this.py + Reference.TILE_SIZE / 2);
		}
	}
}
